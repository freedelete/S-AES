package frame;

import algorithm.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int[][] Input = SAESGUI.Input;  
    static int[][] key = SAESGUI.key; 
	
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Create the frame.
     */
    public MainFrame() {
    	setTitle("S-AES 加密");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setBounds(350,250,1125, 600);  
        setLayout(null);  

        JLabel label = new JLabel("输入明文/密文和密钥:");  
        label.setBounds(500, 20, 150, 30);  
        add(label); 

        JTextField plaintextField = new JTextField();  
        plaintextField.setBounds(460, 60, 200, 30);  
        add(plaintextField);  

        JTextField keyField = new JTextField();  
        keyField.setBounds(460, 100, 200, 30);  
        add(keyField);  

        JButton encryptButton = new JButton("加密");  
        encryptButton.setBounds(10, 150, 100, 30);  
        add(encryptButton);  
        
        JButton decryptButton = new JButton("解密");  
        decryptButton.setBounds(120, 150, 100, 30);  
        add(decryptButton);  

        JButton ASCIIencryptButton = new JButton("ASCII加密");  
        ASCIIencryptButton.setBounds(230, 150, 100, 30);  
        add(ASCIIencryptButton); 
        
        JButton ASCIIdecryptButton = new JButton("ASCII解密");  
        ASCIIdecryptButton.setBounds(340, 150, 100, 30);  
        add(ASCIIdecryptButton);
        
        JButton DBencryptButton = new JButton("双重加密");  
        DBencryptButton.setBounds(450, 150, 100, 30);  
        add(DBencryptButton); 
        
        JButton DBdecryptButton = new JButton("双重解密");  
        DBdecryptButton.setBounds(560, 150, 100, 30);  
        add(DBdecryptButton);   
        
        JButton MITMButton = new JButton("中间相遇攻击");  
        MITMButton.setBounds(670, 150, 100, 30);  
        add(MITMButton);
        
        JButton TPencryptButton = new JButton("三重加密");  
        TPencryptButton.setBounds(780, 150, 100, 30);  
        add(TPencryptButton);
        
        JButton TPdecryptButton = new JButton("三重解密");  
        TPdecryptButton.setBounds(890, 150, 100, 30);  
        add(TPdecryptButton);
        
        JButton CBCButton = new JButton("CBC模式");  
        CBCButton.setBounds(1000, 150, 100, 30);  
        add(CBCButton);
        
        JTextArea outputArea = new JTextArea();  
        outputArea.setBounds(10, 200, 1090, 330);  
        outputArea.setEditable(false);  
        add(outputArea);          
         
        encryptButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String plaintext = plaintextField.getText();  
                String keyInput = keyField.getText();  

                if (plaintext.length() != 16 || keyInput.length() != 16) {  
                    outputArea.setText("明文和密钥必须是16位!");  
                    return;  
                }  

                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                    	Input[i][j] = Integer.parseInt(String.valueOf(plaintext.charAt(i * 8 + j)));  
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  
                
                SAESGUI.encrypt();
                
                //显示密文  
                StringBuilder cipherText = new StringBuilder();  
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                        cipherText.append(Input[i][j]);  
                    }  
                }  
                outputArea.setText("密文: " + cipherText.toString());  
            }  
        });  
        
        decryptButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {                  
            	String cipherText = plaintextField.getText();  
                String keyInput = keyField.getText();  

                if (cipherText.length() != 16 || keyInput.length() != 16) {  
                    outputArea.setText("密文和密钥必须是16位!");  
                    return;  
                }  

                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                        Input[i][j] = Integer.parseInt(String.valueOf(cipherText.charAt(i * 8 + j)));  
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  
		        
                SAESGUI.decrypt();
                
		        // 输出明文
		        StringBuilder plaintext = new StringBuilder();  
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                    	plaintext.append(Input[i][j]);  
                    }  
                }  
		        outputArea.setText("明文: " + plaintext.toString()); 
            }            
        });  
        
        ASCIIencryptButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String plaintext = plaintextField.getText();  
                String keyInput = keyField.getText();  
                
                if (keyInput.length() != 16) {  
                    outputArea.setText("密钥必须是16位!");  
                    return;  
                } 
                
                StringBuilder encryptedResult = new StringBuilder(); // 用于存储加密结果  
                for (char c : plaintext.toCharArray()) { // 遍历输入字符串中的每个字符  
                    String binaryString = SAESGUI.charToBinaryString(c); // 转换为二进制字符串  
                    
                    for (int i = 0; i < 2; i++) {  
                        for (int j = 0; j < 8; j++) {  
                        	Input[i][j] = Integer.parseInt(String.valueOf(binaryString.charAt(i * 8 + j)));  
                            key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                        }  
                    }  
    		        
                    SAESGUI.encrypt();
                    
                    StringBuilder cipherText = new StringBuilder();  
                    for (int i = 0; i < 2; i++) {  
                        for (int j = 0; j < 8; j++) {  
                            cipherText.append(Input[i][j]);  
                        }  
                    }  
                    
                    char r=SAESGUI.binaryStringToChar(cipherText.toString());	// 转换为对应字符串
                    encryptedResult.append(r); // 连接加密后的输出  
                    
                    outputArea.setText("ASCII密文: " + encryptedResult.toString());
                }  
         
            }            
        });  

        ASCIIdecryptButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String plaintext = plaintextField.getText();  
                String keyInput = keyField.getText();  
                
                if (keyInput.length() != 16) {  
                    outputArea.setText("密钥必须是16位!");  
                    return;  
                } 
                
                StringBuilder encryptedResult = new StringBuilder(); // 用于存储加密结果  
                for (char c : plaintext.toCharArray()) { // 遍历输入字符串中的每个字符  
                    String binaryString = SAESGUI.charToBinaryString(c); // 转换为二进制字符串  
                    
                    for (int i = 0; i < 2; i++) {  
                        for (int j = 0; j < 8; j++) {  
                        	Input[i][j] = Integer.parseInt(String.valueOf(binaryString.charAt(i * 8 + j)));  
                            key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                        }  
                    }  
    		        
                    SAESGUI.decrypt();
                    
                    StringBuilder cipherText = new StringBuilder();  
                    for (int i = 0; i < 2; i++) {  
                        for (int j = 0; j < 8; j++) {  
                            cipherText.append(Input[i][j]);  
                        }  
                    }  
                    
                    char r=SAESGUI.binaryStringToChar(cipherText.toString());	// 转换为对应字符串
                    encryptedResult.append(r); // 连接解密后的输出  
                    
                    outputArea.setText("ASCII明文: "+encryptedResult.toString() );
                }  
         
            }            
        });      
        
        DBencryptButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String plaintext = plaintextField.getText();  
                String keyInput = keyField.getText();  

                if (plaintext.length() != 16) {  
                    outputArea.setText("明文必须是16位!");  
                    return;  
                }  
                
                if (keyInput.length() != 32) {  
                    outputArea.setText("密钥必须是32位!");  
                    return;  
                }                   

                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                    	Input[i][j] = Integer.parseInt(String.valueOf(plaintext.charAt(i * 8 + j)));  
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  
               
                SAESGUI.encrypt();
                
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {                      	
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt((i+2) * 8 + j)));  
                    }  
                }  
                
                SAESGUI.encrypt();
                
                //显示密文  
                StringBuilder cipherText = new StringBuilder();  
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                        cipherText.append(Input[i][j]);  
                    }  
                }  
                outputArea.setText("双重加密后密文: " + cipherText.toString());  
                
            }  
        });  
        
        DBdecryptButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String plaintext = plaintextField.getText();  
                String keyInput = keyField.getText();  
                
                if (plaintext.length() != 16) {  
                    outputArea.setText("明文必须是16位!");  
                    return;  
                }  
                
                if (keyInput.length() != 32) {  
                    outputArea.setText("密钥必须是32位!");  
                    return;  
                }   

                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                    	Input[i][j] = Integer.parseInt(String.valueOf(plaintext.charAt(i * 8 + j)));  
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt((i+2) * 8 + j)));  
                    }  
                }  
                
                SAESGUI.decrypt();
                
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {                      	
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  
                
                SAESGUI.decrypt();
                
                //显示密文  
                StringBuilder cipherText = new StringBuilder();  
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                        cipherText.append(Input[i][j]);  
                    }  
                }  
                outputArea.setText("双重解密后明文: " + cipherText.toString());  
            }  
        });  
        
        MITMButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
            	String plaintext = plaintextField.getText();  
                String cipherText = keyField.getText();  

                if (plaintext.length() != 16 || cipherText.length() != 16) {  
                    outputArea.setText("明文和密钥必须是16位!");  
                    return;  
                }  

                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                    	Input[i][j] = Integer.parseInt(String.valueOf(plaintext.charAt(i * 8 + j)));                       
                    }  
                }  
            	
            	// 存储中间加密结果及其对应密钥的映射  
                Map<String, int[][]> encryptionMap = new HashMap<>();  

                // 遍历所有可能的 K1 密钥（为例设定有限的密钥空间）  
                for (int i = 0; i < (1 << 16); i++) { // 假设 K1 为 16 位  
                    int[][] key1 = SAESGUI.generateKey(i); // 根据循环索引生成密钥  
                    SAESGUI.key = key1;  

                    // 加密明文以寻找中间结果  
                    SAESGUI.encrypt();  

                    String intermediateState =  SAESGUI.stateToString(SAESGUI.Input);  
                    encryptionMap.put(intermediateState, key1);  
                }  
                
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                    	Input[i][j] = Integer.parseInt(String.valueOf(cipherText.charAt(i * 8 + j)));                       
                    }  
                }  

                // 尝试使用不同的 K2 解密密文  
                for (int j = 0; j < (1 << 16); j++) { // 假设 K2 也是 16 位  
                    int[][] key2 =  SAESGUI.generateKey(j); // 生成 K2  
                    SAESGUI.key = key2;  

                    // 解密密文以寻找中间结果  
                    SAESGUI.decrypt();  

                    String intermediateState =  SAESGUI.stateToString(SAESGUI.Input);  
                    
                    // 检查中间状态是否存在于加密映射中  
                    if (encryptionMap.containsKey(intermediateState)) {  
                        int[][]k1Found = encryptionMap.get(intermediateState);  
                        outputArea.setText("找到可能的密钥对：K1: " +  SAESGUI.stateToString(k1Found)   
                                           + ", K2: " +  SAESGUI.stateToString(key2));  
                    }  
                }  
            } 
        });  
        
        TPencryptButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String plaintext = plaintextField.getText();  
                String keyInput = keyField.getText();  

                if (plaintext.length() != 16) {  
                    outputArea.setText("明文必须是16位!");  
                    return;  
                }  
                
                if (keyInput.length() != 32) {  
                    outputArea.setText("密钥必须是32位!");  
                    return;  
                }                   

                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                    	Input[i][j] = Integer.parseInt(String.valueOf(plaintext.charAt(i * 8 + j)));  
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  
               
                SAESGUI.encrypt();
                
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {                      	
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt((i+2) * 8 + j)));  
                    }  
                }  
                
                SAESGUI.decrypt();
                
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {                      	
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  
                
                SAESGUI.encrypt();
                
                //显示密文  
                StringBuilder cipherText = new StringBuilder();  
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                        cipherText.append(Input[i][j]);  
                    }  
                }  
                outputArea.setText("三重加密后密文: " + cipherText.toString());  
                
            }  
        });  
        
        TPdecryptButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String plaintext = plaintextField.getText();  
                String keyInput = keyField.getText();  

                if (plaintext.length() != 16) {  
                    outputArea.setText("明文必须是16位!");  
                    return;  
                }  
                
                if (keyInput.length() != 32) {  
                    outputArea.setText("密钥必须是32位!");  
                    return;  
                }    
                
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {                      	
                    	Input[i][j] = Integer.parseInt(String.valueOf(plaintext.charAt(i * 8 + j)));  
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  

                SAESGUI.decrypt();
                
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {                      	
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt((i+2) * 8 + j)));  
                    }  
                }  
                
                SAESGUI.encrypt();
                
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {                      	
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  
                
                SAESGUI.decrypt();
                
                //显示密文  
                StringBuilder cipherText = new StringBuilder();  
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                        cipherText.append(Input[i][j]);  
                    }  
                }  
                outputArea.setText("三重解密后明文: " + cipherText.toString());  
            }  
        });  
        
        CBCButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                String plaintext = plaintextField.getText();  
                String keyInput = keyField.getText();  
               
                for (int i = 0; i < 2; i++) {  
                    for (int j = 0; j < 8; j++) {  
                    	Input[i][j] = Integer.parseInt(String.valueOf(plaintext.charAt(i * 8 + j)));  
                        key[i][j] = Integer.parseInt(String.valueOf(keyInput.charAt(i * 8 + j)));  
                    }  
                }  
                
                SAESCBC.result(Input);
                
                outputArea.setText("IV为"+SAESGUI.stateToString(SAESCBC.iv)+'\n'
                		+"加密后的密文: " + SAESGUI.stateToString(SAESCBC.ciphertextOriginal)+'\n'
	                	+"篡改后的密文: " + SAESGUI.stateToString(SAESCBC.ciphertext)+'\n'
	                	+"篡改后解密的结果: " + SAESGUI.stateToString(SAESCBC.decrypted)+'\n'
	                	+"正常解密的结果: " + SAESGUI.stateToString(SAESCBC.normalDecrypted));  
            }  
        });  
    }
    
    	
}