package vip.wqby.learnserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import vip.wqby.learnserver.model.auto.Member;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    private static final String SECRET = "wangquanbaye6666";
    //token签发者
    private static final String ISSUSRE = "wqby";
    //token过期时间
    public static final Long EXPIRE_DATE = 1000 * 60L * 60 * 24 * 7; //7天过期


    public static String Token(Member member) {
        //当前时间
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = JWT.create()
                .withIssuer(ISSUSRE)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + EXPIRE_DATE))
                .withClaim("username", member.getUsername())
                .withClaim("password", member.getPassword())
                .withClaim("memberId", member.getId())
                .sign(algorithm);
        return token;
    }

    public static String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token).getClaim("username").asString();
    }

    public static String getMemberIdFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUSRE).build().verify(token).getClaim("memberId").asInt().toString();
    }

    public static String createToken(Map<String, String> map) {
        //创建过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);  //7天过期

        //创建builder对象
        JWTCreator.Builder builder = JWT.create();
        //遍历map
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 验证token
     * 验证过程中如果有异常，则抛出；
     * 如果没有,则返回 DecodedJWT 对象来获取用户信息;
     *
     * @param token
     */
    public static boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
            return true;
        } catch (SignatureVerificationException e) {
            //验证的签名不一致
//            throw new SignatureVerificationException(algorithm);
        } catch (TokenExpiredException e) {
//            throw new TokenExpiredException("token is alreadey expired");
        } catch (AlgorithmMismatchException e) {
//            throw new AlgorithmMismatchException("签名算法不匹配");
        } catch (InvalidClaimException e) {
//            throw new InvalidClaimException("校验的claims内容不匹配");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证token
     * 验证过程中如果有异常，则抛出；
     * 如果没有,则返回 DecodedJWT 对象来获取用户信息;
     *
     * @param token
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }
}
