package org.springmore.commons.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * <pre>
 * 单DES加密算法 . 默认加密方式:DES/ECB/NOPadding 选择此加密方式，
 * 待加密数据的长度必须是8的倍数 可选的加密方式有：
 * DES/ECB/PKCS5Padding 
 * 加密算法理论参考:http://aub.iteye.com/blog/1131504
 * </pre>
 * 
 * @author 唐延波
 * @date 2014-10-24 上午10:23:56
 */
public class DESUtil {

	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "DES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/NOPadding";

	// private static final String DEFAULT_CIPHER_ALGORITHM_1 =
	// "DES/ECB/PKCS5Padding";

	/**
	 * 生成密钥.
	 * @return
	 * @throws Exception
	 * @author 唐延波
	 * @date 2015-6-9
	 */
	public static byte[] generateSecretKey() throws Exception {
		// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化此密钥生成器，使其具有确定的密钥大小
		kg.init(56);
		// 生成一个密钥
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	

	/**
	 * 加密 默认加密方式：DES/ECB/NOPadding
	 * 
	 * @param data
	 *            待加密的二进制数据
	 * @param key
	 *            二进制密钥
	 * @return 加密后的数据
	 * @throws Exception
	 * @author 唐延波
	 * @date 2015-6-9
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密 可指定加密方式
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            二进制密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm)
			throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待加解密的二进制数据
	 * @param key
	 *            二进制密钥
	 * @return 解密后的数据
	 * @throws Exception
	 * @author 唐延波
	 * @date 2015-6-9
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}
	
	

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key,
			String cipherAlgorithm) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		// 实例化DES密钥规则
		DESKeySpec dks = new DESKeySpec(key);
		// 实例化密钥工厂
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		// 生成密钥
		SecretKey secretKey = skf.generateSecret(dks);
		return secretKey;
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm)
			throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm)
			throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

}