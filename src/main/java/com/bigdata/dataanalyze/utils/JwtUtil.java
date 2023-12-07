package com.bigdata.dataanalyze.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
//    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 秘钥
     */
    private static final String SECRET = "my_secret";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 1800L;//单位为秒

    /**
     * 根据key，生成调用大模型的token
     */
    public  static String createToken(String key){
        //过期时间
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("sign_type", "SIGN");
        HashMap<String, String> payload = new HashMap<>();
        payload.put("api_key", key);
        payload.put("exp", String.valueOf(System.currentTimeMillis() + EXPIRATION * 1000000000));
        payload.put("timestamp", String.valueOf(System.currentTimeMillis()));
        String token= JWT.create()
                .withHeader(map)
                //添加头部
                .withPayload(payload)
                //可以把数据存在claim中
//                .withClaim("id",user.getId())      //userId
//                .withClaim("name",user.getName())
//                .withClaim("userName",user.getUserName())
//                .withExpiresAt(expireDate)          //超时设置,设置过期的日期
//                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }


}