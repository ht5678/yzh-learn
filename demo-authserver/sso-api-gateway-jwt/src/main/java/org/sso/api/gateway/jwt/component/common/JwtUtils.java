package org.sso.api.gateway.jwt.component.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

/**
 * jwt工具类
 * @author yuezh2@lenovo.com
 *	@date 2021年11月10日下午4:42:15
 */
public class JwtUtils {
	
	private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 秘钥
     *
     */
    private final static String SECRET = "demodemodemodemodemodemodemodemodemodemodemodemodemodemodemodemodemo";
    /**
     * 有效期，单位秒
     */
    private final static Long expirationTimeInSecond=3600l;

    /**
     * 方法实现说明:从jwt中解析Claims(Hashmap) 里面封装了用户的信息
     * @param token jwt
     * @return:
     * @exception:
     */
    public static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("token解析错误", e);
            throw new IllegalArgumentException("Token invalided.");
        }
    }

    /**
     * 方法实现说明:获取jwt的过期时间
     * @param token jwt
     * @return:
     * @exception:
     */
    public static Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token)
                .getExpiration();
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     * @return 已过期返回true，未过期返回false
     */

    /**
     * 方法实现说明:判断jwt是否过期
     * @param token jwt令牌
     * @return: true 表示过期,false 标识没有过期
     * @exception:
     */
    private static Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 方法实现说明:计算过期时间
     * @return: 过期时间
     * @exception:
     */
    private static Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + expirationTimeInSecond * 1000);
    }

    /**
     * 为指定用户生成token
     *
     * @param claims 用户信息
     * @return token
     */

    /**
     * 支持的算法详见：https://github.com/jwtk/jjwt#features
     * 方法实现说明
     * @param claims 用户的信息
     * @return:
     * @exception:
     */
    public static String generateJwt(Map<String, Object> claims) {
        Date createdTime = new Date();
        Date expirationTime = getExpirationTime();


        byte[] keyBytes = SECRET.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder().setClaims(claims).setIssuedAt(createdTime)
                .setExpiration(expirationTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * 方法实现说明 校验token
     * @param token jwt
     * @return: 未过期返回true，否则返回false
     * @exception:
     * @date:2020/3/11 15:40
     */
    public static Boolean validateJwt(String token) {
        return !isTokenExpired(token);
    }

    public static void main(String[] args) {

        // 2.设置用户信息
        HashMap<String, Object> claims = Maps.newHashMap();
        claims.put("id", "1");
        claims.put("userName","张三");

        // 生成jwt
        String jwt = JwtUtils.generateJwt(claims);
        // 会生成类似该字符串的内容:  aaaa.bbbb.cccc
       log.info("生成的jwt:{}",jwt);

       //校验jwt
       Boolean validateFlag = validateJwt("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aG9yaXplLXNlcnZlciIsImFwaS1nYXRld2F5IiwicHJvZHVjdC1zZXJ2aWNlIiwib3JkZXItc2VydmljZSJdLCJwaG9uZSI6bnVsbCwidXNlcl9uYW1lIjoiemhhbmdzYW4iLCJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTYzNjU0MTgwMiwidXNlcklkIjoyLCJhdXRob3JpdGllcyI6WyIvb3JkZXIvc2VsZWN0T3JkZXJJbmZvQnlJZEFuZFVzZXJOYW1lIiwiL3Byb2R1Y3Qvc2VsZWN0UHJvZHVjdEluZm9CeUlkIl0sImp0aSI6ImM5ZWViODAyLTdhOTQtNDY2ZS04YzgwLTU0NWM3YjNmNWFjYSIsImVtYWlsIjoiemhhbmdzYW5AMTI2LmNvbSIsImNsaWVudF9pZCI6InBvcnRhbF9hcHAifQ.ITKhII0arR4zzam1vOsB0QsgANHYgopG4e1KZ5s9ZEMDHhQEoJ_iJ5Fzxrpu4H1Kzdzz-xKE1if0b0j50x4UY9X_Jc-OXhKE4SaKQNqQot06IlGv107qp0AZvylNbM2Wni8MWKzK-P91Zgk_S5-nuwwZYVfZNrbjB2TBpnSW9BiY91MjXD16sRzzWvlvmFR_OCL3WG6Ag6iAAcPAbRbWuVDA7vgJ3JzFNyb2cRDMqbvfRJ7wLU-wKNuh55pUMwqK6MjPo051U4YNr-LV6qFvZZ4EMxlZD50hNi2GovcseJLFKeKk0xmKDINvXPdpwLt0pt7ZkHpAsOG6q01IggWjNQ\",\"token_type\":\"bearer\",\"refresh_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aG9yaXplLXNlcnZlciIsImFwaS1nYXRld2F5IiwicHJvZHVjdC1zZXJ2aWNlIiwib3JkZXItc2VydmljZSJdLCJwaG9uZSI6bnVsbCwidXNlcl9uYW1lIjoiemhhbmdzYW4iLCJzY29wZSI6WyJyZWFkIl0sImF0aSI6ImM5ZWViODAyLTdhOTQtNDY2ZS04YzgwLTU0NWM3YjNmNWFjYSIsImV4cCI6MTYzNjU0MzYwMiwidXNlcklkIjoyLCJhdXRob3JpdGllcyI6WyIvb3JkZXIvc2VsZWN0T3JkZXJJbmZvQnlJZEFuZFVzZXJOYW1lIiwiL3Byb2R1Y3Qvc2VsZWN0UHJvZHVjdEluZm9CeUlkIl0sImp0aSI6IjliNDk5ODM4LTBjYWUtNGJhYS1hZmNiLWM0ZmMxYmQ4ZjYzYiIsImVtYWlsIjoiemhhbmdzYW5AMTI2LmNvbSIsImNsaWVudF9pZCI6InBvcnRhbF9hcHAifQ.bNMVVo2p99V7L0LUpA4GY4cy4JaG8ItnlBe9XBcPzznOb9XTdOnUx8rCONBJ_gDrmB0mPZNTDtb2Uz-eRAvLnodBHJBWDO0vlgSDrR4xfEzXxoB8q6Fsn9ju-K1B9BK4cBNMp--R5FWH6l5w2b_8pGoLyUEkW1LwdBPz0PiOXGQzezBIqO5dHmjCwzr4fB0ytLIGT_XfTc8AZCE3UCC79Sl_5e9jUiGMgPRkc2-R--PCOJlYd0VdZ5SX5MLouuiT__Zj5NtMoQVm4CI_ImV_6xpQOkrHF9F8amK80_fsMzPYbh5VuFjfRCmvozQOeIRDRHI_ALYH5Jmcf65g-yIf8g");
       log.info("校验jwt结果:{}",validateFlag);

        //异常校验jwt
        /*Boolean validateFlag2 = validateJwt(jwt+2);
        log.info("校验jwt结果:{}",validateFlag2);*/

        //解析jwt的头部信息
        String jwtHeader = jwt.split("\\.")[0];
        log.info("头部信息:{}",jwtHeader);
        byte[] header = Base64.decodeBase64(jwtHeader.getBytes());
        log.info("解密头部信息:{}",new String(header));

        String pload = jwt.split("\\.")[1];
        log.info("pload信息:{}",pload);
        byte[] jwtPload = Base64.decodeBase64(pload.getBytes());
        log.info("解密头部信息:{}",new String(jwtPload));


    }



}
