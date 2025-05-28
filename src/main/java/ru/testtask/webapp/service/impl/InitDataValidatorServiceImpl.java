package ru.testtask.webapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.testtask.webapp.config.AppProperties;
import ru.testtask.webapp.service.InitDataValidatorService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDataValidatorServiceImpl implements InitDataValidatorService, InitializingBean {

    private static final String LINE_FEED_CHARACTER = "\n";
    private static final String DEFAULT_CRYPTO_KEY = "WebAppData";

    private final AppProperties appProperties;

    private String botToken;
    private String cryptoKey;

    @Override
    public boolean validate(String initData) {
        if (initData == null || initData.isEmpty()) {
            throw new IllegalArgumentException("InitData cannot be null or empty");
        }
        try {
            Map<String, String> initDataFieldsMap = mapInitData(initData);
            String dataCheckString = buildCheckString(initDataFieldsMap);
            return verifyHash(dataCheckString, initDataFieldsMap.get("hash"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to validate init data: " + e.getMessage(), e);
        }
    }

    private String buildCheckString(Map<String, String> fieldValuesMap) {
        Map<String, String> data = new TreeMap<>(fieldValuesMap);
        data.remove("hash");
        if (data.isEmpty()) {
            throw new IllegalArgumentException("InitData is required to create check-string");
        }
        StringBuffer sb = new StringBuffer();
        data.forEach((fieldName, fieldValue) -> {
            sb.append(fieldName);
            sb.append("=");
            sb.append(fieldValue);
            sb.append(LINE_FEED_CHARACTER);
        });
        sb.setLength(sb.length() - 1);
        log.info("data-check-string: {}", sb);
        return sb.toString();
    }

    private boolean verifyHash(String checkString, String expectedHash) {
        String actualHash = computeCheckStringHash(checkString);
        return actualHash.equals(expectedHash);
    }

    private String computeCheckStringHash(String checkString) {
        byte[] key = new HmacUtils(HMAC_SHA_256, cryptoKey).hmac(botToken);
        HmacUtils mac = new HmacUtils(HMAC_SHA_256, key);
        return mac.hmacHex(checkString);
    }

    private Map<String, String> mapInitData(String initData) {
        return Arrays.stream(initData.split("&"))
                .map(param -> param.split("=", 2))
                .collect(Collectors.toMap(
                        arr -> URLDecoder.decode(arr[0], StandardCharsets.UTF_8),
                        arr -> arr.length > 1 ? URLDecoder.decode(arr[1], StandardCharsets.UTF_8) : "",
                        (existing, lastValue) -> lastValue,
                        HashMap::new
                ));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.botToken = Optional.ofNullable(appProperties).map(AppProperties::getBotToken)
                .orElseThrow(() -> new IllegalStateException("Bot token is not defined"));
        this.cryptoKey = Optional.ofNullable(appProperties.getCryptoKey())
                .orElseGet(() -> DEFAULT_CRYPTO_KEY);
        log.info("App properties successfully read and set. Crypto key is [{}]", this.cryptoKey);
    }
}