package org.spring.DAO.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class RedisDAO {

    private final JedisPool jedisPool;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDAO(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }

    public Seckill getSeckill(long id){
        try{
            Jedis jedis = jedisPool.getResource();
            try {

                String key = "seckill:"+id;
                byte[] bytes = jedis.get(key.getBytes());

                if (bytes!=null){
                    Seckill seckill = schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes,seckill,schema);
                    //seckill 被反序列
                    return seckill;
                }

            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return null;
    }

    public String putSeckill(Seckill seckill){
        try{
            Jedis jedis = jedisPool.getResource();
            try {

                String key = "seckill:"+seckill.getSeckillId();
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                String result = jedis.setex(key.getBytes(), 60 * 60, bytes);

                return result;
            }finally {
                jedis.close();
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return null;
    }
}
