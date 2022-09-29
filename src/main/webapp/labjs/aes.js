/** require aeslib.js */

/**
 * generate a random AES key
 * @param {int} size byte number of the key, a multiple of 32
 * @returns a string of the key
 */
function AESGenerateKey(size) {
    var arr = new Array(size);
    for (var i = 0; i < arr.length; i++){
        // get random numbers
        arr[i] = 32+Math.floor(Math.random() * 96);
    }
    // convert arr into a string according to ASCII code
    var key = String.fromCharCode.apply(null, arr);
    return key;    
}

/**
 * AES encrypt using key to encrypt plainText
 * @param {string} key AES key, represented by a string
 * @param {string} plainText plain text
 * @returns cipher text
 */
function AESEncrypt(AESKey, plainText) {
    var key = CryptoJS.enc.Utf8.parse(AESKey);
    var srcs = CryptoJS.enc.Utf8.parse(plainText);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    return encrypted.toString();
}

/**
 * AES decrypt using key to decrypt cipherText
 * @param {string} AESKey AES key, represented by a string
 * @param {string} cipherText cipher text
 * @returns plain text
 */
function AESDecrypt(AESKey, cipherText) {
    var key = CryptoJS.enc.Utf8.parse(AESKey);
    var decrypt = CryptoJS.AES.decrypt(cipherText, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}
