package com.jrako.candela.discovery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.candela.ConfigurationException;
import com.candela.House;
import com.candela.discovery.HouseConfigurationResolver;

public class AprHouseConfigurationResolver implements HouseConfigurationResolver {

    private static final String HOUSE_ID_PROPERTY_KEY = "com.jrako.apr.house.id";
    private static final int DEFAULT_HOUSE_ID = 1;

    private int houseId = DEFAULT_HOUSE_ID;

    @Override
    public House resolve() throws ConfigurationException {
        updateHouseId();
        AprIpAddressResolver ipAddressResolver = new AprIpAddressResolver();
        ipAddressResolver.initialise();
        String address = ipAddressResolver.resolveIpAddress();
        String xmlData = fetchXmlConfig(address);
        AprXmlConfigParser parser = new AprXmlConfigParser(houseId, xmlData);
        House house = parser.parseXml();
        return house;
    }

    private void updateHouseId() {
        String houseIdStr = System.getProperty(HOUSE_ID_PROPERTY_KEY);
        if (StringUtils.isNotEmpty(houseIdStr) && StringUtils.isNumeric(houseIdStr)) {
            houseId = Integer.parseInt(houseIdStr);
        }
    }

    private String fetchXmlConfig(String hostName) {
        String xml = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://" + hostName + "/rako.xml");
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            ByteArrayOutputStream stream = new ByteArrayOutputStream(4096);
            entity.writeTo(stream);
            xml = new String(stream.toByteArray());
            IOUtils.closeQuietly(stream);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }
}
