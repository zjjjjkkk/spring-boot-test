package top.zjk.boot.redis.service.impl;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zjk.boot.redis.cache.RedisCache;
import top.zjk.boot.redis.cache.RedisKeys;
import top.zjk.boot.redis.config.CloopenConfig;
import top.zjk.boot.redis.enums.ErrorCode;
import top.zjk.boot.redis.exception.ServerException;
import top.zjk.boot.redis.service.SmsService;
import top.zjk.boot.redis.utils.CommonUtils;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class SmsServiceImpl implements SmsService {
    private final CloopenConfig cloopenConfig;
    private final RedisCache redisCache;

    @Override
    public boolean sendSms(String phone) {
        if (!CommonUtils.checkPhone(phone)) {
            throw new ServerException(ErrorCode.PHONE_ERROR);
        }
        int code = CommonUtils.generateCode();
        redisCache.set(RedisKeys.getSmsKey(phone), code, 60);
        log.info("发送短信验证码:{}", code);
        return send(phone, code);
    }

    private boolean send(String phone, int code) {
        // ===== 调试：确认配置注入成功 =====
        log.info(">>> CloopenConfig = {}", cloopenConfig);
        log.info(">>> serverIp={}, port={}", cloopenConfig.getServerIp(), cloopenConfig.getPort());

        String serverIp   = cloopenConfig.getServerIp();
        String serverPort = cloopenConfig.getPort();
        String accountSid = cloopenConfig.getAccountSid();
        String accountToken = cloopenConfig.getAccountToken();
        String appId      = cloopenConfig.getAppId();
        String templateId = cloopenConfig.getTemplateId();

        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSid, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);

        String[] datas = {String.valueOf(code), "1"};
        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId, datas, "1234", UUID.randomUUID().toString());

        if ("000000".equals(result.get("statusCode"))) {
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                log.info("{} = {}", key, object);
            }
            return true;
        } else {
            log.error("错误码={} 错误信息= {}", result.get("statusCode"), result.get("statusMsg"));
            return false;
        }
    }
}