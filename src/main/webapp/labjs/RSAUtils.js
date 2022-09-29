
/**
 * 
 * @param {BigInteger} privateExponent 
 * @param {BigInteger} modulus
 */
function RSAPrivateKey(privateExponent, modulus) {
    this.exponent = privateExponent;
    this.modulus = modulus;
}

/**
 * 
 * @param {BigInteger} publicExponent
 * @param {BigInteger} modulus
 */
function RSAPublicKey(publicExponent, modulus) {
    this.exponent = publicExponent;
    this.modulus = modulus;
}

/**
 * 
 * @param {string} s the string need to be converted
 * @returns a BigInt value of s
 */
function str2BigInteger(s) {
    var bytes = s.split('');
    var result = new BigInteger("0");
    for (var i = 0; i < bytes.length; i++){
        // console.log(bytes[i].charCodeAt().toString(16))
        result = result.shiftLeft(8);
        result = result.add(new BigInteger(bytes[i].charCodeAt().toString()));
        // console.log(bytes[i].charCodeAt())
        // console.log(bytes[i].charCodeAt().toString());
    }
    // console.log(result.toString(16))
    return result;
}

/**
 * example:
 * 0x616263 -> abc
 * @param {BigInteger} bigInteger the big int need to be converted
 * @returns a string
 */
function bigInteger2str(bigInteger) {
    var result = '';
    while (bigInteger.compareTo(new BigInteger("0")) > 0) {
        var lastByte = bigInteger.mod(new BigInteger("100", 16));
        lastByte = Number(lastByte);
        // console.log(lastByte)
        var lastByteStr = String.fromCharCode(lastByte);
        result = lastByteStr + result;
        bigInteger = bigInteger.shiftRight(8);
    }
    return result;
}

/**
 * 
 * @param {RSAPublicKey} rsaPublicKey contains exponent and modulus
 * @param {String} plainText a string of plain text
 * @returns a big int encrypted
 */
function RSAEncrypt(rsaPublicKey, plainText) {
    var plainTextBigInteger = str2BigInteger(plainText);
    var modulus = rsaPublicKey.modulus;
    var exponent = rsaPublicKey.exponent;
    // var encryptResult = (plainTextBigInteger ** exponent) % modulus;
    // console.log(exponent)
    var encryptResult = plainTextBigInteger.modPow(exponent, modulus);
    return encryptResult;
}


/**
 * 
 * @param {RSAPrivateKey} rsaPrivateKey contains exponent and modulus
 * @param {BigInt} cipherTextBigInteger 
 * @returns the string decrypted
 */
function RSADecrypt(rsaPrivateKey, cipherTextBigInteger) {
    var modulus = rsaPrivateKey.modulus;
    var exponent = rsaPrivateKey.exponent;
    // var plainTextBigInteger = (cipherTextBigInteger ** exponent) % modulus;
    var plainTextBigInteger = cipherTextBigInteger.modPow(exponent, modulus);
    var decryptResult = bigInteger2str(plainTextBigInteger);
    return decryptResult;
}
