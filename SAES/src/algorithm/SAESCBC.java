package algorithm;  

import java.util.Random;  

public class SAESCBC {  
    // 引入 SAESGUI 类  
    static final int BLOCK_SIZE = 8; // 定义块大小为8位  
    static final int IV_SIZE = 2; // IV的块数量  
    public static int[][] iv; // 初始向量  
    public static int[][] ciphertext;
    public static int[][] decrypted;
    public static int[][] ciphertextOriginal;
    public static int[][] normalDecrypted;

    public static void result(int[][] plaintext) {  
        // 生成初始向量  
        iv = generateIV(); // 随机生成IV  

        ciphertextOriginal = new int[plaintext.length][BLOCK_SIZE];  
        
        // 加密   
        ciphertext = cbcEncrypt(plaintext);  

        for (int i = 0; i < plaintext.length; i++) {  
            for (int j = 0; j < BLOCK_SIZE; j++) {  
                ciphertextOriginal[i][j] = ciphertext[i][j];  
            }  
        }  
        
        // 篡改密文  
        ciphertext[0][0] = 1 - ciphertext[0][0]; // 反转第一个比特位  
        // 解密  
        decrypted = cbcDecrypt(ciphertext);  

        // 进行正常解密  
        normalDecrypted = cbcDecrypt(ciphertextOriginal); // 使用原密文进行正常解密 （这里 ciphertextOriginal 是未被篡改的）  
    }  

    // 生成随机的初始向量 IV  
    public static int[][] generateIV() {  
        Random random = new Random();  
        int[][] iv = new int[IV_SIZE][BLOCK_SIZE];  
        for (int i = 0; i < IV_SIZE; i++) {  
            for (int j = 0; j < BLOCK_SIZE; j++) {  
                iv[i][j] = random.nextInt(2); // 随机生成0或1  
            }  
        }  
        return iv;  
    }  

    // CBC 加密过程  
    public static int[][] cbcEncrypt(int[][] plaintext) {  
        int blocks = plaintext.length;  
        int[][] ciphertext = new int[blocks][BLOCK_SIZE];  
        int[] previousCipherBlock = new int[BLOCK_SIZE];  

        // 第一个块与 IV XOR  
        for (int i = 0; i < BLOCK_SIZE; i++) {  
            previousCipherBlock[i] = plaintext[0][i] ^ iv[0][i]; // 与初始向量 XOR  
        }  
        SAESGUI.Input = new int[][] {previousCipherBlock.clone(), new int[BLOCK_SIZE]};  

        // 加密第一个块  
        SAESGUI.encrypt();  
        System.arraycopy(SAESGUI.Input[0], 0, ciphertext[0], 0, BLOCK_SIZE);  

        // 对于后续块  
        for (int i = 1; i < blocks; i++) {  
            for (int j = 0; j < BLOCK_SIZE; j++) {  
                previousCipherBlock[j] = plaintext[i][j] ^ ciphertext[i - 1][j]; // 将当前明文块与前一个密文块异或  
            }  
            SAESGUI.Input = new int[][] {previousCipherBlock.clone(), new int[BLOCK_SIZE]};  
            
            // 加密当前块  
            SAESGUI.encrypt();  
            System.arraycopy(SAESGUI.Input[0], 0, ciphertext[i], 0, BLOCK_SIZE); // 存储密文块  
        }  

        return ciphertext;  
    }  

    // CBC 解密过程  
    public static int[][] cbcDecrypt(int[][] ciphertext) {  
        int blocks = ciphertext.length;  
        int[][] decrypted = new int[blocks][BLOCK_SIZE];  
        int[] previousCipherBlock = (blocks > 1) ? ciphertext[blocks - 2] : iv[0]; // 对于第一个块使用IV  

        for (int i = 0; i < blocks; i++) {  
            SAESGUI.Input = new int[][] {ciphertext[i].clone(), new int[BLOCK_SIZE]};  
            
            // 解密当前密文块  
            SAESGUI.decrypt();  
            for (int j = 0; j < BLOCK_SIZE; j++) {  
                decrypted[i][j] = SAESGUI.Input[0][j] ^ previousCipherBlock[j]; // 将解密结果与前一个密文块异或  
            }  

            previousCipherBlock = ciphertext[i]; // 更新前一个密文块  
        }  

        return decrypted;  
    }  
}