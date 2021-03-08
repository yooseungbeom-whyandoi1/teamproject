package ksmart38.mybatis.encryption;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.encryption.pbe.config.StringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {
	
	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor pooledPBEStringEncryptor=new PooledPBEStringEncryptor();
		SimpleStringPBEConfig simpleStringPBEConfig =  new  SimpleStringPBEConfig();

		simpleStringPBEConfig.setPassword("test"); //암호화에 사용할 키 (대칭키)-> 중요
        simpleStringPBEConfig.setAlgorithm("PBEWithMD5AndDES"); //사용할 암호하 알고리즘
        simpleStringPBEConfig.setKeyObtentionIterations("1000"); //몇번돌려서 키얻을래?
        simpleStringPBEConfig.setPoolSize("1");
//      simpleStringPBEConfig.setProviderName("SunJCE");
        simpleStringPBEConfig.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); //암호문이 랜덤으로 그때마다 다르게 암호화
        simpleStringPBEConfig.setStringOutputType("base64"); //암호문 64진법
        pooledPBEStringEncryptor.setConfig(simpleStringPBEConfig);
        return pooledPBEStringEncryptor;
	}
}
