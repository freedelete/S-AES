package algorithm; 

public class SAESGUI  {  
    static final int[][] s = {  
            {9, 4, 10, 11},  
            {13, 1, 8, 5},  
            {6, 2, 0, 3},  
            {12, 14, 15, 7}  
    };  
    
    static final int[][] s_inv = {
            {10, 5, 9, 11},
            {1, 7, 8, 15},
            {6, 0, 2, 3},
            {12, 4, 13, 14}
        };
    
    public static int[][] Input = new int[2][8];  
    public static int[][] key = new int[2][8];  
    public static int[][] k1Found;
    public static int[][] key2;

    static final int[][] tihuanwei = {  
            {0, 0, 0, 0},  
            {0, 0, 0, 1},  
            {0, 0, 1, 0},  
            {0, 0, 1, 1},  
            {0, 1, 0, 0},  
            {0, 1, 0, 1},  
            {0, 1, 1, 0},  
            {0, 1, 1, 1},  
            {1, 0, 0, 0},  
            {1, 0, 0, 1},  
            {1, 0, 1, 0},  
            {1, 0, 1, 1},  
            {1, 1, 0, 0},  
            {1, 1, 0, 1},  
            {1, 1, 1, 0},  
            {1, 1, 1, 1}  
    };  

    static final int[] rcon1 = {1, 0, 0, 0, 0, 0, 0, 0};  
    static final int[] rcon2 = {0, 0, 1, 1, 0, 0, 0, 0};  

    public static int[] yihuo8(int[] a, int[] b) {  
        int[] t = new int[8];  
        for (int i = 0; i < 8; i++)  
            t[i] = a[i] ^ b[i];  
        return t;  
    }  

    public static int[] yihuo4(int[] a, int[] b) {  
        int[] t = new int[4];  
        for (int i = 0; i < 4; i++)  
            t[i] = a[i] ^ b[i];  
        return t;  
    }  

    public static void s_he_tihuan(int[] temp) {  
        int t1 = 2 * temp[0] + temp[1];  
        int t2 = 2 * temp[2] + temp[3];  
        int t3 = 2 * temp[4] + temp[5];  
        int t4 = 2 * temp[6] + temp[7];  
        int tihuan1 = s[t1][t2];  
        int tihuan2 = s[t3][t4];  

        for (int i = 0; i < 4; i++)  
            temp[i] = tihuanwei[tihuan1][i];  
        for (int i = 0; i < 4; i++)  
            temp[i + 4] = tihuanwei[tihuan2][i];  
    }          

    public static void zuoyi(int[][] temp) {  
        for (int i = 4; i < 8; i++) {  
            int t = temp[0][i];  
            temp[0][i] = temp[1][i];  
            temp[1][i] = t;  
        }  
    }  

    public static int[] g(int[] temp, int[] rcon) {  
        int[] t = temp.clone(); // Create a copy  
        for (int i = 0; i < 4; i++) {  
            int tt = t[i + 4];  
            t[i + 4] = t[i];  
            t[i] = tt;  
        }  

        s_he_tihuan(t);  
        return yihuo8(t, rcon);  
    }  

    public static void liehunxiao(int[][] mingwen) {  
        int[] si_de2jinzhi = {0, 1, 0, 0};  
        int[] m00 = new int[4];  
        int[] m10 = new int[4];  
        int[] m01 = new int[4];  
        int[] m11 = new int[4];  
        for (int i = 0; i < 4; i++) {  
            m00[i] = mingwen[0][i];  
            m10[i] = mingwen[0][i + 4];  
            m01[i] = mingwen[1][i];  
            m11[i] = mingwen[1][i + 4];  
        }  

        int[] n00 = yihuo4(m00, yihuo4(si_de2jinzhi, m10));  
        int[] n10 = yihuo4(m10, yihuo4(si_de2jinzhi, m00));  
        int[] n01 = yihuo4(m01, yihuo4(si_de2jinzhi, m11));  
        int[] n11 = yihuo4(yihuo4(si_de2jinzhi, m01), m11);  

        for (int i = 0; i < 4; i++) {  
            mingwen[0][i] = n00[i];  
            mingwen[0][i + 4] = n10[i];  
            mingwen[1][i] = n01[i];  
            mingwen[1][i + 4] = n11[i];  
        }  
    }  

    public static void lunmiyaojia(int[][] mingwen, int[][] key) {  
        for (int i = 0; i < 2; i++)  
            for (int j = 0; j < 8; j++)  
                mingwen[i][j] ^= key[i][j];  
    }  
   
    public static void s_inv_tihuan(int[] temp) {
        int t1 = 2 * temp[0] + temp[1];
        int t2 = 2 * temp[2] + temp[3];
        int t3 = 2 * temp[4] + temp[5];
        int t4 = 2 * temp[6] + temp[7];
        int tihuan1 = s_inv[t1][t2];
        int tihuan2 = s_inv[t3][t4];
        for (int i = 0; i < 4; i++) {
            temp[i] = tihuanwei[tihuan1][i];
        }
        for (int i = 0; i < 4; i++) {
            temp[i + 4] = tihuanwei[tihuan2][i];
        }
    }
    
    public static void decrypt(){
	    int[][] key1 = new int[2][8];
	    int[][] key2 = new int[2][8];
	    key1[0] = yihuo8(key[0], g(key[1], rcon1));
	    key1[1] = yihuo8(key1[0], key[1]);
	    key2[0] = yihuo8(key1[0], g(key1[1], rcon2));
	    key2[1] = yihuo8(key2[0], key1[1]);
    
	    // 反向第二轮
	    lunmiyaojia(Input, key2);
	    zuoyi(Input);
	    s_inv_tihuan(Input[0]);
	    s_inv_tihuan(Input[1]);
	
	    // 反向第一轮
	    lunmiyaojia(Input, key1);
	    liehunxiao(Input);
	    zuoyi(Input);
	    s_inv_tihuan(Input[0]);
	    s_inv_tihuan(Input[1]);
	    lunmiyaojia(Input, key);
	}
    
    public static void encrypt() {
    	// 密钥扩展算法，只进行两轮加密  
        int[][] key1 = new int[2][8];  
        int[][] key2 = new int[2][8];  

        key1[0] = yihuo8(key[0], g(key[1], rcon1));  
        key1[1] = yihuo8(key1[0], key[1]);  
        key2[0] = yihuo8(key1[0], g(key1[1], rcon2));  
        key2[1] = yihuo8(key2[0], key1[1]);  

        lunmiyaojia(Input, key);  

        // 第一轮  
        s_he_tihuan(Input[0]);  
        s_he_tihuan(Input[1]);  
        zuoyi(Input);  
        liehunxiao(Input);  
        lunmiyaojia(Input, key1);  

        // 第二轮  
        s_he_tihuan(Input[0]);  
        s_he_tihuan(Input[1]);  
        zuoyi(Input);  
        lunmiyaojia(Input, key2);  

    }
   
    // 字符转为十六位二进制  
    public static String charToBinaryString(char c) {  
        return String.format("%16s", Integer.toBinaryString(c)).replace(' ', '0'); 
    }

    // 将二进制字符串转换回字符  
    public static char binaryStringToChar(String binary) {  
        return (char) Integer.parseInt(binary, 2);   
    }  
    

    // 生成密钥的函数 
    public static int[][] generateKey(int i) {  
        int[][] key = new int[2][8];  
        for (int j = 0; j < 16; j++) {  
            key[j / 8][j % 8] = (i >> (15 - j)) & 1; // 根据位设置密钥的每一位  
        }  
        return key;  
    }  

    // 将输入状态转换为字符串表示  
    public static String stateToString(int[][] state) {  
        StringBuilder sb = new StringBuilder();  
        for (int[] arr : state) {  
            for (int bit : arr) {  
                sb.append(bit);  
            }  
        }  
        return sb.toString();  
    }     
}  

/*
    public static void shuchu(int[][] a) {  
        StringBuilder output = new StringBuilder();  
        for (int i = 0; i < 2; i++) {  
            for (int j = 0; j < 8; j++) {  
                output.append(a[i][j]).append(" ");  
            }  
        }  
        System.out.println(output.toString().trim());  
    }  
*/ 
