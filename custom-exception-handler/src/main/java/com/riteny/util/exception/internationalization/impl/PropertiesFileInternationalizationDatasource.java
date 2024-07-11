package com.riteny.util.exception.internationalization.impl;


import com.riteny.util.exception.internationalization.InternationalizationDatasource;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesFileInternationalizationDatasource implements InternationalizationDatasource {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesFileInternationalizationDatasource.class);

    public PropertiesFileInternationalizationDatasource() {

        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/exception-i18n.json")) {

            if (resourceAsStream == null) {
                logger.error("Read properties for internationalization datasource failed . ");
                throw new RuntimeException("Read properties for internationalization datasource failed . ");
            }

            byte[] bytes = new byte[resourceAsStream.available()];

            int readSize = resourceAsStream.read(bytes);
            logger.info("Profile size [{}]. ", readSize);

            String jsonStr = new String(bytes);

            JSONObject propertiesJson = JSONObject.fromObject(jsonStr);

            Iterator<?> propertiesKeyIterator = propertiesJson.keys();
            while (propertiesKeyIterator.hasNext()) {

                String key = propertiesKeyIterator.next().toString();
                logger.info("Internationalization profile key [ {} ]. ", key);

                JSONObject value = JSONObject.fromObject(propertiesJson.get(key));

                Iterator<?> profileKeyIterator = value.keys();
                while (profileKeyIterator.hasNext()) {
                    String index = profileKeyIterator.next().toString();
                    Map<String, String> contextMap = internationalizationProfileMap.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
                    contextMap.put(index, value.getString(index));
                    logger.info("Internationalization profile value [ key = {} , value = {} ]. ", index, value.getString(index));
                }
            }

        } catch (Exception e) {
            logger.error("Internationalization datasource init failed . ");
            throw new RuntimeException("Internationalization datasource init failed . ", e);
        }
    }

    @Override
    public String getValue(String index, String lang) {

        Map<String, String> contextMap = internationalizationProfileMap.get(lang);

        if (contextMap == null) {
            return index;
        }

        return contextMap.get(index);
    }
}
