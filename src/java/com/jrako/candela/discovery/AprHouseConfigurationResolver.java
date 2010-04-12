package com.jrako.candela.discovery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.candela.House;
import com.candela.discovery.ConfigurationException;
import com.candela.discovery.HouseConfigurationResolver;

public class AprHouseConfigurationResolver implements HouseConfigurationResolver {

    private static final String BRIDGE_NAME_PROPERTY_KEY = "rako.apr.netbios.name";
    private static final String HOUSE_ID_PROPERTY_KEY = "rako.apr.house.id";
    private static final String DEFAULT_BRIDGE_NAME = "RAKOBRIDGE";
    private static final int DEFAULT_HOUSE_ID = 1;

    private String bridgeNetbiosName = DEFAULT_BRIDGE_NAME;
    private int houseId = DEFAULT_HOUSE_ID;
    private final NetbiosNameResolver nameResolver;

    public AprHouseConfigurationResolver() {
        nameResolver = new JcifsNetbiosNameResolver();
    }

    @Override
    public House resolve() throws ConfigurationException {
        updateBridgeName();
        updateHouseId();
        String address = resolveIpAddress();
        String xmlData = fetchXmlConfig(address);
        AprXmlConfigParser parser = new AprXmlConfigParser(houseId, xmlData);
        House house = parser.parseXml();
        return house;
    }

    private String resolveIpAddress() throws ConfigurationException {
        String ipAddress = null;
        try {
            InetAddress address = nameResolver.resolveAddress(bridgeNetbiosName);
            ipAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
            throw new ConfigurationException("Could not resolve IP address for netbios name: " + bridgeNetbiosName, e);
        }
        return ipAddress;
    }

    private void updateHouseId() {
        String newName = System.getProperty(BRIDGE_NAME_PROPERTY_KEY);
        if (StringUtils.isNotEmpty(newName)) {
            bridgeNetbiosName = newName;
        }
    }

    private void updateBridgeName() {
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
