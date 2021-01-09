package com.yunxin.utils.security.utils;

import com.yunxin.utils.Work;
import com.yunxin.utils.bytes.UnPack;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class EdchCrypt {

    static{
        try{
            Security.addProvider(new BouncyCastleProvider());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    String DEFAULT_PUB_KEY = "020b03cf3d99541f29ffec281bebbd4ea211292ac1f53d7128";
    String DEFAULT_SHARE_KEY = "4da0f614fc9f29c2054c77048a6566d7";

    String S_PUB_KEY= "04928D8850673088B343264E0C6BACB8496D697799F37211DEB25BB73906CB089FEA9639B4E0260498B51A992D50813DA8";
    String X509_S_PUB_KEY = "3046301006072A8648CE3D020106052B8104001F03320004928D8850673088B343264E0C6BACB8496D697799F37211DEB25BB73906CB089FEA9639B4E0260498B51A992D50813DA8";

    private byte[] _g_share_key;
    private byte[] _c_pub_key;
    private PublicKey x509PublicKey;
    private PrivateKey pkcs8PrivateKey;

    public int initShareKeyByBouncycastle() throws Throwable{

//        String str1 = buf_to_string(string_to_buf(SvrPubKey));
//        String str2 = new StringBuilder().append("3059301306072a8648ce3d020106082a8648ce3d030107034200").append(str1).toString();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC","BC");
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp192k1");
        keyPairGenerator.initialize(ecGenParameterSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey1 = keyPair.getPublic();
        byte[] publicKeyBytes = publicKey1.getEncoded();

        PrivateKey privateKey = keyPair.getPrivate();
        privateKey.getEncoded();

        PublicKey publicKey2 = constructX509PublicKey(X509_S_PUB_KEY);
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH","BC");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey2,true);
        UnPack unPack = new UnPack(keyAgreement.generateSecret());
        _g_share_key = Work.md5(unPack.getAll());
        _c_pub_key = new byte[49];
        System.arraycopy(publicKeyBytes,23,_c_pub_key,0,49);
        x509PublicKey = publicKey1;
        pkcs8PrivateKey = privateKey;
        System.out.println("initShareKeyByBouncycastle OK");
        return 0;
    }

    private PublicKey constructX509PublicKey(String paramString) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {

        return KeyFactory.getInstance("EC", "BC").generatePublic(new X509EncodedKeySpec(string_to_buf(paramString)));
    }

    public static String buf_to_string(byte[] paramArrayOfbyte) {
        if (paramArrayOfbyte == null)
            return "";
        String str = "";
        byte b = 0;
        while (true) {
            String str1 = str;
            if (b < paramArrayOfbyte.length) {
                str = str + Integer.toHexString(paramArrayOfbyte[b] >> 4 & 0xF);
                str = str + Integer.toHexString(paramArrayOfbyte[b] & 0xF);
                b++;
                continue;
            }
            return str1;
        }
    }
    public static byte[] string_to_buf(String paramString) {
        byte b = 0;
        if (paramString == null)
            return new byte[0];
        byte[] arrayOfByte = new byte[paramString.length() / 2];
        while (b < paramString.length() / 2) {
            arrayOfByte[b] = (byte)(byte)((get_char((byte)paramString.charAt(b * 2)) << 4) + get_char((byte)paramString.charAt(b * 2 + 1)));
            b++;
        }
        return arrayOfByte;
    }

    public static byte get_char(byte paramByte) {
        if (paramByte >= 48 && paramByte <= 57) {
            paramByte = (byte)(paramByte - 48);
            return paramByte;
        }
        if (paramByte >= 97 && paramByte <= 102) {
            paramByte = (byte)(paramByte - 97 + 10);
            return paramByte;
        }
        if (paramByte >= 65 && paramByte <= 70) {
            paramByte = (byte)(paramByte - 65 + 10);
            return paramByte;
        }
        paramByte = 0;
        return paramByte;
    }

    public byte[] calShareKeyByBouncycastle(byte[] paramArrayOfbyte) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException {
        String str = "3046301006072A8648CE3D020106052B8104001F03320004";
        if (paramArrayOfbyte.length < 30)
            str = "302E301006072A8648CE3D020106052B8104001F031A00";
        String str1 = buf_to_string(paramArrayOfbyte);
        StringBuilder stringBuilder3 = new StringBuilder();
        PublicKey publicKey = constructX509PublicKey(stringBuilder3.append(str).append(str1).toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        System.out.println(stringBuilder2.append("raw public key ").append(buf_to_string(_c_pub_key)));
        stringBuilder2 = new StringBuilder();
        System.out.println(stringBuilder2.append("pkcs8PrivateKey ").append(pkcs8PrivateKey.toString()).toString());
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH","BC");
        keyAgreement.init(pkcs8PrivateKey);
        keyAgreement.doPhase(publicKey,true);
        byte[] arrayOfByte2 = keyAgreement.generateSecret();
        UnPack unPack = new UnPack(arrayOfByte2);
        byte[] arrayOfByte1 = Work.md5(unPack.getAll());
        stringBuilder3 = new StringBuilder();
        System.out.println(stringBuilder3.append("calc share key: ").append(buf_to_string(arrayOfByte2)));
        System.out.println("share key md5 "+buf_to_string(arrayOfByte1));
        paramArrayOfbyte = arrayOfByte1;
        return paramArrayOfbyte;

    }

    public byte[] calShareKeyMd5ByPeerPublicKey(byte[] array) throws InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException {
        return calShareKeyByBouncycastle(array);
    }

    public static void int8_to_buf(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
        paramArrayOfbyte[paramInt1 + 0] = (byte)(byte)(paramInt2 >> 0);
    }


    public static void main(String[] args) throws Throwable {
        EdchCrypt edchCrypt = new EdchCrypt();
        int r = edchCrypt.initShareKeyByBouncycastle();
        System.out.println("_c_pub_key: "+Work.Bytes.hex(edchCrypt._c_pub_key).replace(" ","")+", "+edchCrypt._c_pub_key.length);
        System.out.println("_g_share_key: "+Work.Bytes.hex(edchCrypt._g_share_key).replace(" ","")+", "+edchCrypt._g_share_key.length);
        System.out.println(r);

        String pk = "04 94 16 63 F9 9B DB B2 C4 C7 02 2D 66 3A \n" +
                "7A F1 9F 1D C4 C6 75 44 A8 4D FE 42 16 A6 CB F7 \n" +
                "B5 99 CC 27 69 AF 03 5C 01 F3 8D 90 A5 3F 30 D5 \n" +
                "A8 19 0D";

        String ddd="56 FA 16 3A 3B AD 71 1D 4E D2 CA 4C A6 \n" +
                "8B 4A B0 BF C8 BB 98 BC C6 9D 6C FD E8 2F F3 F1 \n" +
                "B8 5F 87 FA 95 DA 16 E1 5B E9 50 01 2C 66 96 19 \n" +
                "87 2D A4 B6 9A BA 54 F8 3C 2A 81 42 CA 10 F6 9C \n" +
                "A0 68 F7 DB BF 84 66 63 DE F4 AC 27 12 B0 90 68 \n" +
                "C8 D7 84 D5 86 78 7F 51 E7 F8 31 52 62 D5 34 9B \n" +
                "E4 C2 B3 F4 59 88 B6 F3 E0 DE 92 C0 1B AB 24 A1 \n" +
                "C2 0C 11 B8 46 15 1F 03 FF 5B 0E 90 72 08 8E 96 \n" +
                "19 89 50 4E 0F 4F 2C B7 47 64 43 35 7C 4C FF 22 \n" +
                "0F D4 26 41 D5 0A 9F 41 8C E9 7A 48 73 56 02 E8 \n" +
                "83 2C ED 18 41 2B 7C CA 85 60 0D 55 A3 E9 59 89 \n" +
                "32 50 45 D6 EF 08 05 66 3E AE C2 85 64 CE FE BC \n" +
                "9B E1 30 93 A0 D2 DA DA B5 91 B6 08 5F CA 66 C1 \n" +
                "16 AD CD F6 85 72 54 C6 1C F1 21 6D C5 CB 76 C8 \n" +
                "D3 B7 9C 4B AF 8E 4E 1A B7 71 7A C0 07 A0 52 27 \n" +
                "13 58 9A BC 2B 3E 1D B4 7C E6 0E 28 BD 93 A9 30 \n" +
                "86 75 F7 58 1F E7 1A 5C AF 21 99 38 24 F6 D3 D9 \n" +
                "B0 95 86 75 DD 07 76 13 29 87 AA D6 38 59 8D 7B \n" +
                "7B 8D 76 93 5B 81 9F 26 64 E3 88 0C AF 5C 88 1C \n" +
                "85 94 24 59 C1 02 9C 4F 2C 12 10 4A F1 7D 65 17 \n" +
                "55 29 84 3F B4 79 B9 EB A6 11 A4 F2 61 58 58 2D \n" +
                "D1 35 F9 7B 5D 31 34 47 FC C0 EC E8 F7 8E 54 15 \n" +
                "FD A3 4C 2A 54 E8 7A C5 7F 63 8A 4E 1B AB 66 7D \n" +
                "78 CE 26 B3 8D F0 29 A1 3F 99 31 13 C4 52 A8 48 \n" +
                "3E 1A 5A A9 75 65 A2 27 02 C9 7E 5E C1 D2 6F EB \n" +
                "F4 6B 27 70 7B 51 1C BD AA F0 29 98 2E 92 51 FF \n" +
                "25 AF A5 C2 AB 3E 26 19 3A C7 69 B5 14 B3 42 42 \n" +
                "47 9E 54 49 DA 0F 8F 44 05 17 93 8D 7D 87 4E F6 \n" +
                "73 4A B9 05 51 66 49 BE FE 3E 4E 2E 4E 1D 03 3B \n" +
                "1F 95 0F CC 2C A0 22 06 80 28 51 0B 82 A1 E0 F7 \n" +
                "8A 40 C4 96 27 A4 2F 22 7C 44 4B 13 79 A1 9C C2 \n" +
                "4B F5 BD 53 EA DC 48 DD FC 61 34 A3 95 B3 D3 61 \n" +
                "50 10 DA D1 53 EE 0A 35 1B BE 07 94 A1 C5 CC CD \n" +
                "BA C0 EA A2 B2 F2 B5 B0 61 50 13 54 2E 0E EF EA \n" +
                "30 0A 48 43 11 CD 6B C1 E5 3C FE A9 A4 15 DF 29 \n" +
                "01 4D 50 D4 41 49 2F B7 33 06 0C 2F 42 05 10 2F \n" +
                "0D 6E 74 0D EF 99 74 D9 E7 EA EF 25 B1 38 BA 5A \n" +
                "99 C0 FD 8D 8C 94 A3 70 3B 97 B8 04 D7 74 25 43 \n" +
                "42 B7 3C 8F 40 FC 61 D8 DC 32 E6 CA 58 FC AA FA \n" +
                "56 C6 A3 68 BB D6 87 4F 22 18 AD DF D3 27 0D 6C \n" +
                "79 D0 04 B5 7B CF EC A1 13 2D 37 2C AC D4 56 A7 \n" +
                "05 7F A4 CD 52 43 18 9D A7 EC 8F 34 80 2B 20 C1 \n" +
                "99 33 5D 94 43 51 7C 6D 43 72 AB 32 F0 EA 0E 57 \n" +
                "A6 D0 33 56 5E 78 94 33 85 B5 AA 7A 8B 1A 27 92 \n" +
                "0D D3 4C AF 55 FA 90 7C DC 89 B2 45 AE 55 C2 ED \n" +
                "59 3D 32 03 CE D7 C8 0D A2 34 4C 18 D2 61 AD B7 \n" +
                "23 E7 CF A5 A1 34 E0 B1 1F DA C3 B9 E7 3F 22 BB \n" +
                "DF 48 49 09 D9 50 0D DD B4 DF B9 A6 8C 2A 43 05 \n" +
                "5F 3E 08 BC DA 81 34 CC 69 75 5C 8B 1F 71 6C BF \n" +
                "15 1E 99 E3 07 05 65 28 5A ED 9F 53 E1 25 1B C3 \n" +
                "27 4E 7E 69 67 3C A3 7F 1B F7 A2 91 DA 13 BE 91 \n" +
                "57 1A B5 94 EA 0A 1B 73 93 5B 9F 41 9B 1A F4 8F \n" +
                "3C DE A0 39 7F 5B F5 A3 71 85 65 ";

        byte[] d = edchCrypt.calShareKeyMd5ByPeerPublicKey(Work.Bytes.bytes(pk));
//        byte[] d1 = edchCrypt.calShareKeyMd5ByPeerPublicKey(edchCrypt._c_pub_key);
//
        System.out.println("cal share key md: "+Work.Bytes.hex(d).replace("",""));
//        System.out.println("cal share key md1: "+Work.Bytes.hex(d1).replace("",""));
//        System.out.println(d);

        byte[] allD = Work.Bytes.bytes(ddd);

        System.out.println(cryptor.decrypt(allD,0,allD.length,edchCrypt._g_share_key));





//        byte[] key = Work.Bytes.bytesWithFills(16, (byte) 0);
//        byte[] data = new byte[]{0,0,0};
//        byte[] encrypted = cryptor.encrypt(data,0,3,key);
//        System.out.println(Work.Bytes.hex(encrypted));
//        System.out.println(Work.Bytes.hex(cryptor.decrypt(encrypted,0,encrypted.length,key)));
//        byte[] ecc = Tea.encrypt(data,key);
//        System.out.println(Work.Bytes.hex(ecc));
//        System.out.println(Work.Bytes.hex(Tea.decrypt(ecc,key)));
//
//        byte[] cc = {1,2,3};
//        int8_to_buf(cc,0,999);
//        System.out.println(Work.Bytes.hex(cc));

//        oicq.wlogin_sdk.tools.EcdhCrypt c = new EcdhCrypt(null);
//        byte[] d = c.calShareKeyMd5ByPeerPublicKey(new byte[]{1});
//        System.out.println(d);
    }
}
