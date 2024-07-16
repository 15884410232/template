

import {
    v4 as uuidv4
} from 'uuid';
import md5 from 'js-md5';
import keyutils from 'js-crypto-key-utils';
import rsa from 'js-crypto-rsa';
import CryptoJS from 'crypto-js';
import _ from 'lodash';

import env from '../config/dev'

import store from '../store/index'

var _mime = function (option, value) {
    var mimeTypes = navigator.mimeTypes;
    for (var mt in mimeTypes) {
        if (mimeTypes[mt][option] == value) {
            return true;
        }
    }
    return false;
};

if (!Number.prototype.formatAmount) {
    Number.prototype.formatAmount = function (decPlaces) {
        decPlaces = _.isNaN(decPlaces = Math.abs(decPlaces)) ? 2 : decPlaces;
        var n = this.toFixed(decPlaces);
        if (decPlaces) {
            var i = n.substr(0, n.length - (decPlaces + 1));
            var j = '.' + n.substr(-decPlaces);
        } else {
            i = n;
            j = '';
        }

        function reverse (str) {
            var sr = '';
            for (var l = str.length - 1; l >= 0; l--) {
                sr += str.charAt(l);
            }
            return sr;
        }

        if (parseInt(i)) {
            i = reverse(reverse(i).replace(/(\d{3})(?=\d)/g, "$1" + ','));
        }
        return i + j;
    };
}

if (!String.prototype.format) {
    String.prototype.format = function (args) {
        if (arguments.length > 0) {
            var result = this;
            if (arguments.length == 1 && typeof (args) == "object") {
                for (var key in args) {
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            } else {
                for (var i = 0; i < arguments.length; i++) {
                    if (arguments[i] == undefined) {
                        return "";
                    } else {
                        var reg = new RegExp("({[" + i + "]})", "g");
                        result = result.replace(reg, arguments[i]);
                    }
                }
            }
            return result;
        } else {
            return this;
        }
    }
}


class CommonFunc {
    numToChinese (money, currency) {
        let moneyCopy = money;
        let negSuffix = '(负数)';
        money = Math.abs(money);
        //汉字的数字        
        var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
        //基本单位
        var cnIntRadice = new Array('', '拾', '佰', '仟');
        //对应整数部分扩展单位
        var cnIntUnits = new Array('', '万', '亿', '兆');
        //对应小数部分单位
        var cnDecUnits = new Array('角', '分', '毫', '厘');
        //整数金额时后面跟的字符
        var cnInteger = '整';
        //整型完以后的单位
        var cnIntLast = '元';
        //最大处理的数字
        var maxNum = 999999999999999.9999;
        //金额整数部分
        var integerNum;
        //金额小数部分
        var decimalNum;
        //输出的中文金额字符串
        var chineseStr = '';
        //分离金额后用的数组，预定义
        var parts;
        if (money == '') {
            return '';
        }
        money = parseFloat(money);
        if (money >= maxNum) {
            //超出最大处理数字
            return '';
        }
        if (money == 0) {
            chineseStr = cnNums[0] + cnIntLast + cnInteger;
            return chineseStr;
        }
        //转换为字符串
        money = money.toString();
        if (money.indexOf('.') == -1) {
            integerNum = money;
            decimalNum = '';
        } else {
            parts = money.split('.');
            integerNum = parts[0];
            decimalNum = parts[1].substr(0, 4);
        }
        //获取整型部分转换
        if (parseInt(integerNum, 10) > 0) {
            var zeroCount = 0;
            var IntLen = integerNum.length;
            for (var i = 0; i < IntLen; i++) {
                var n = integerNum.substr(i, 1);
                var p = IntLen - i - 1;
                var q = p / 4;
                var m = p % 4;
                if (n == '0') {
                    zeroCount++;
                } else {
                    if (zeroCount > 0) {
                        chineseStr += cnNums[0];
                    }
                    //归零
                    zeroCount = 0;
                    chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
                }
                if (m == 0 && zeroCount < 4) {
                    chineseStr += cnIntUnits[q];
                }
            }
            chineseStr += cnIntLast;
        }
        //小数部分
        if (decimalNum != '') {
            var decLen = decimalNum.length;
            for (var i = 0; i < decLen; i++) {
                var n = decimalNum.substr(i, 1);
                if (n != '0') {
                    chineseStr += cnNums[Number(n)] + cnDecUnits[i];
                }
            }
        }
        if (chineseStr == '') {
            chineseStr += cnNums[0] + cnIntLast + cnInteger;
        } else if (decimalNum == '') {
            chineseStr += cnInteger;
        }
        moneyCopy = parseFloat(moneyCopy);
        if (moneyCopy < 0) {
            return negSuffix + chineseStr;
        }
        return chineseStr;
    }
    thousandToNum (num) {
        if (num) {
            var str = num + "";
            if (str.indexOf(',') > -1) {
                num = str.replace(/,/gi, '');
            }
            return num;
        } else {
            return null;
        }
    }
    numberWithCommas (x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
    isNullOrEmpty (obj) {
        //判断顺序不能变
        if (
            _.isUndefined(obj) ||
            _.isNull(obj) ||
            (_.isString(obj) && obj.trim() === "") ||
            (_.isArray(obj) && _.isEmpty(obj))
        ) {
            return true;
        }
        return false;
    }
    round (num, decimals) {
        if (this.isNullOrEmpty(num) || _.isNaN(parseFloat(num))) {
            return num;
        }



        return parseFloat(num).formatAmount(decimals);
    }
    isChina (s) {
        var pattern = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
        if (!pattern.exec(s)) {
            return false;
        } else {
            return true;
        }
    }
    getBrowserInfo () {
        var ua = navigator.userAgent.toLocaleLowerCase();
        var browserType = null;
        if (ua.match(/msie/) != null || ua.match(/trident/) != null) {
            browserType = "IE";
        } else if (ua.match(/firefox/) != null) {
            browserType = "火狐";
        } else if (ua.match(/ubrowser/) != null) {
            browserType = "UC";
        } else if (ua.match(/opera/) != null) {
            browserType = "欧朋";
        } else if (ua.match(/bidubrowser/) != null) {
            browserType = "百度";
        } else if (ua.match(/metasr/) != null) {
            browserType = "搜狗";
        } else if (ua.match(/tencenttraveler/) != null || ua.match(/qqbrowse/) != null) {
            browserType = "QQ";
        } else if (ua.match(/maxthon/) != null) {
            browserType = "遨游";
        } else if (ua.match(/chrome/) != null) {
            var is360 = _mime("type", "application/vnd.chromium.remoting-viewer");
            if (is360) {
                browserType = '360';
            } else {
                browserType = 'chrome';
            }
        } else if (ua.match(/safari/) != null) {
            browserType = "Safari";
        }

        return browserType;
    }

    periodToDate (obj) {
        if (!this.isNullOrEmpty(obj)) {
            obj = obj.toString();
            var year = obj.substring(0, 4);
            var month = obj.substring(4);
            var date = year + "-" + month + "-01"; //默认为1号
            return new Date(date);
        }
    }
    //对象自动转换为数组
    objectToArray (myObject) {
        var newArray = Object.values(myObject);
        var target = [];
        for (var i = 0, len = newArray.length; i < len; i++) {
            target.push(newArray[i]);
        }
        return target;
    }
    isArray (obj) {
        return obj instanceof Array;
    }

    newGuid () {
        function s4 () {
            return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
        };
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
    }
    //动态构造table
    populateTable (columnList, valueList) {
        var div = '<div style="max-height:300px; overflow:auto;">';
        var table = '';
        var tableHeader = '<table class="table table-striped table-hover table-bordered" style="text-align:left">';
        var tbody = '';
        var th = '';

        columnList.forEach(function (item) {
            th += '<th>' + item + '</th>';
        });
        var thead = '<tr>' + th + '</tr>';

        valueList.forEach(function (row) {
            var tr = '';
            columnList.forEach(function (item) {
                tr += '<td>' + row[item] + '</td>';
            });

            tbody += '<tr>' + tr + '</tr>';
        });

        table = div + tableHeader + thead + tbody + '</table>' + '</div>';

        // console.log(table);

        return table;
    }


    uuid () {
        return uuidv4();
    }
    signature (config) {
        let signature = '';
        let signToStr = '';
        try {
            let param = '';
            let url = '';
            if (!!config.baseURL) {
                url = (config.baseURL + config.url.split('?')[0]).replace(env.BASE_API_URL, '')
            }
            else {
                url = config.url.split('?')[0].replace(env.BASE_API_URL, '')
            }
            if (url[0] != '/') {
                url = '/' + url;
            }
            // console.log('url:', url);
            param = config.url.split('?')[1];
            param = !!param ? param : '';
            if (config.method.toLowerCase() != 'get') {
                console.log("config")
                console.log(config.data)
                param = param + md5(JSON.stringify(!!config.data ? config.data : {}));
            }
            console.log('param:', param);
            param = config.method.toLowerCase() + '/' + param;
            console.log('param:', param);
            const uuid = this.newGuid();
            const key = 'fontent';
            const token = store.getters.token
            const timeSpan = Date.parse(new Date());

            url = encodeURI(url);
            param = encodeURI(param);
            signToStr = `url=${url}&param=${param}&uuid=${uuid}&timeSpan=${timeSpan}&key=${key}`;
            console.log("signToStr")
            console.log(signToStr)
            console.log('token:', token);
            signature = `sign=${window.btoa(CryptoJS.HmacSHA256(signToStr, token).toString())}&uuid=${uuid}&timeSpan=${timeSpan}&key=${key}`;

            console.log('signature:', signature);
        } catch (e) {
            console.log(e);
        }
        return signature;
    }
    pcode (list) {
        const lStr = JSON.stringify(list);
        const token = 'apex-backend';
        return CryptoJS.HmacSHA256(lStr, token).toString();
    }
    aseEncrypt (message, keyStr) {
        var key = CryptoJS.enc.Utf8.parse(keyStr);
        var srcs = CryptoJS.enc.Utf8.parse(message);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return encrypted.toString();
    }
    aesDecrypt (message, keyStr) {
        var key = CryptoJS.enc.Utf8.parse(keyStr);
        var decrypt = CryptoJS.AES.decrypt(message, key, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();
    }
    // 另一种rsa公钥加载加密实现
    async getRsaEncode (key, msg) {
        const pem = '-----BEGIN PUBLIC KEY-----\n' + key + '\n-----END PUBLIC KEY-----';
        const keyObj = new keyutils.Key('pem', pem);
        let jwk;
        if (!keyObj.isEncrypted) {
            jwk = await keyObj.export('jwk');
        }
        return new Promise((resolve, reject) => {
            try {
                rsa.encrypt(
                    msg,
                    jwk,
                    'SHA-256', // optional, for OAEP. default is 'SHA-256'
                ).then((encrypted) => {
                    resolve(encrypted);
                })
            } catch (ex) {
                reject();
            }
        });
    }

    pagination (pageNo, pageSize, array) {
        var offset = (pageNo - 1) * pageSize;
        return (offset + pageSize >= array.length) ? array.slice(offset, array.length) : array.slice(offset, offset + pageSize);
    }

    //YYYYMM 202107 获取相差的月数
    getBetweenMonths (date1, date2) {
        date1 = parseInt(date1.substr(0, 4)) * 12 + parseInt(date1.substr(4, 2))
        date2 = parseInt(date2.substr(0, 4)) * 12 + parseInt(date2.substr(4, 2))
        return Math.abs(date1 - date2);
    }

    //2021-Q1   2020-Q4 获取两个季度 的 相差季度数量
    getOverMaxQuarter (date1, date2) {
        let date1Year = parseInt(date1.substr(0, 4));
        let date1Quarter = parseInt(date1.substr(6, 1));

        let date2Year = parseInt(date2.substr(0, 4));
        let date2Quarter = parseInt(date2.substr(6, 1));


        let number = 0;

        if (date1Year > date2Year) {
            return number;
        }

        if (date1Year === date2Year && date1Quarter > date2Quarter) {
            return number;
        }

        if (date1Year <= date2Year) {
            number = 5 - date1Quarter;
        }

        for (let a = date1Year + 1; a <= date2Year; a++) {
            let max = a === date2Year ? date2Quarter : 4;
            for (let b = 1; b <= max; b++) {
                number++;
            }
        }

        return number;
    }

    getCurQuarStart () {
        let year = new Date().getFullYear();
        let month = new Date().getMonth() + 1;
        let result = "";
        switch (month) {
            case 1:
            case 2:
            case 3:
                result = (year - 1).toString() + "-Q1";
                break;
            case 4:
            case 5:
            case 6:
                result = (year - 1).toString() + "-Q2";
                break;
            case 7:
            case 8:
            case 9:
                result = (year - 1).toString() + "-Q3";
                break;
            case 10:
            case 11:
            case 12:
                result = (year - 1).toString() + "-Q4";
                break;
            default:
                break;
        }

        return result;
    }

    getCurQuarEnd () {
        let year = new Date().getFullYear();
        let month = new Date().getMonth() + 1;
        let result = "";
        switch (month) {
            case 1:
            case 2:
            case 3:
                result = (year - 1).toString() + "-Q4";
                break;
            case 4:
            case 5:
            case 6:
                result = year + "-Q1";
                break;
            case 7:
            case 8:
            case 9:
                result = year + "-Q2";
                break;
            case 10:
            case 11:
            case 12:
                result = year + "-Q3";
                break;
            default:
                break;
        }

        return result;
    }
    finKeyInUrl (variable) {
        let url = window.location.href
        if (url.indexOf(variable) === -1) {
            return ''
        }
        let params = url.substring(url.indexOf('?') + 1);

        let array = params.split('&');
        if (array && array.length > 0) {
            for (let i = 0; i < array.length; i++) {
                let pair = array[i].split('=');
                if (pair && pair.length > 1 && pair[0] === variable) {
                    return pair[1]
                }
            }
        }

        return ''
    }
    getCurrentMonth () {
        var nowDate = new Date();
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth();
        if (month == 0) {
            return (year - 1) + "12";
        }
        if (month > 0 && month <= 9) {
            return year + "0" + month;
        }
        else {
            return year + "" + month;
        }
    }
    isNullOrEmpty (str) {
        if (typeof str == "undefined" || str == null || str == "") {
            return true;
        } else {
            return false;

        }
    }
    getLongInt () {
        let min = 1000;
        let max = 100000000;
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    isDate (strDate) {
        var dtDate = null
        var nYear = parseInt(strDate.substring(0, 4), 10)
        var nMonth = parseInt(strDate.substring(5, 7), 10)
        var nDay = parseInt(strDate.substring(8, 10), 10)
        if (isNaN(nYear) === true || isNaN(nMonth) === true || isNaN(nDay) === true) {
            return false
        }
        dtDate = new Date(nYear, nMonth - 1, nDay)
        if (nYear !== dtDate.getFullYear() || (nMonth - 1) !== dtDate.getMonth() || nDay !== dtDate.getDate()) {
            return false
        }
        return true
    }
}

const otherUtil = new CommonFunc();
export default otherUtil