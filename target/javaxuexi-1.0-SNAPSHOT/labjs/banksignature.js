// bank return the result with it's signature
//bank need to return the operation result and the ordernumber
//bank need to encryte the message
// JavaScript.
//
// Requires BigInt.js and Barrett.js and RSAUtils.js and sha_dev.js
//
// Copyright likaiyuan.
//
// You may use, re-use, abuse, copy, and modify this code to your liking, but
// please keep this header.
//
// Thanks!
/**
 * generate a signature
 * @param {String} ordernumber the order of the required
 * @param {String} decision the result of the operation(yes or no)
 * @param {String} date the date of the operation
 * @returns the mac of the message
 */
function Mac(ordernumber,decision,date) {
    var str=ordernumber+date+decision;
    var shaObj1 = new jsSHA("SHA-256", "TEXT");
    shaObj1.update(str);
    var hash1 = shaObj1.getHash("HEX");
    return hash1;
}

/**
 * full encrytion use rsa,aes and hash (if only use elec-envelope the the adversary can use change one bit attack)
 * @param {RSAPublicKey} Pubkey the PubKey
 * @param {String} PrivKey the privkey
 * @param {String} message the message to encrypto
 * @returns the result of the encryption
 */
function EncryptoFull(PubKey,PrivKey,message){
    //First use Pubkey to encrpte Privkey ,priv is the result
    var priv=RSAEncrypt(PubKey,PrivKey).toString();
    //Then use PrivKey to encrypt the message
    var result=AESEncrypt(PrivKey,message);
    //Third we use hash to mac the encrypt result
    var shaObj1 = new jsSHA("SHA-256", "TEXT");
    shaObj1.update(result);
    var hash1 = shaObj1.getHash("HEX");
    //Last we return the result
    return {'Encryption':result,'mac':hash1,'priv':priv};
}

