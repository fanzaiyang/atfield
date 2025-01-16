package cn.fanzy.atfield.core.utils;

import cn.fanzy.atfield.core.model.ssl.SslCertInfo;
import cn.fanzy.atfield.core.model.ssl.SslIssuerPrincipal;
import cn.fanzy.atfield.core.model.ssl.SslSubjectPrincipal;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * SSL 证书实用程序
 *
 * @author fanzaiyang
 * @date 2025/01/16
 */
@Slf4j
public class SslCertUtils {
    /**
     * 信任所有 HTTPS 证书
     */
    public static void trustAllHttpsCertificates() {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new TrustAllHttpsCertificates();
        trustAllCerts[0] = tm;
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 SSL 证书信息
     *
     * @param httpsUrl HTTPS 网址
     * @return {@link SslCertInfo }
     */
    public static SslCertInfo getSslCertInfo(String httpsUrl) {
        if (StrUtil.isBlank(httpsUrl)) {
            return null;
        }
        // 校验 httpsUrl 是否合法
        if (!StrUtil.startWith(httpsUrl, "https://") && !StrUtil.startWith(httpsUrl, "http://")) {
            httpsUrl = "https://" + httpsUrl;
        } else if (StrUtil.startWith(httpsUrl, "http://")) {
            httpsUrl = "https://" + StrUtil.subAfter(httpsUrl, "http://", true);
        }
        log.debug("获取SSL证书信息，域名：{}", httpsUrl);
        trustAllHttpsCertificates();
        try {
            URL url = new URL(httpsUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            url.openConnection();
            connection.connect();
            Certificate[] serverCertificates = connection.getServerCertificates();
            if (serverCertificates == null || serverCertificates.length == 0) {
                return null;
            }
            X509Certificate certificate = (X509Certificate) serverCertificates[0];
            String issuerDN = certificate.getIssuerX500Principal().getName();
            SslIssuerPrincipal issuer = new SslIssuerPrincipal();
            issuer.setIssuerC(StrUtil.subBetween(issuerDN, "C=", ","));
            issuer.setIssuerO(StrUtil.subBetween(issuerDN, "O=", ","));
            issuer.setIssuerOU(StrUtil.subBetween(issuerDN, "OU=", ","));
            issuer.setIssuerCN(StrUtil.subBetween(issuerDN, "CN=", ","));
            String subjectDN = certificate.getSubjectX500Principal().getName();
            SslSubjectPrincipal subject = new SslSubjectPrincipal();
            subject.setSubjectC(StrUtil.subBetween(subjectDN, "C=", ","));
            subject.setSubjectO(StrUtil.subBetween(subjectDN, "O=", ","));
            subject.setSubjectOU(StrUtil.subBetween(subjectDN, "OU=", ","));
            subject.setSubjectCN(StrUtil.subBetween(subjectDN, "CN=", ","));
            return SslCertInfo.builder()
                    .domain(url.getHost())
                    .version(certificate.getVersion())
                    .sigAlgName(certificate.getSigAlgName())
                    .sigAlgOID(certificate.getSigAlgOID())
                    .notBefore(certificate.getNotBefore())
                    .notAfter(certificate.getNotAfter())
                    .issuerX500DN(certificate.getIssuerX500Principal().getName())
                    .subjectX500DN(certificate.getSubjectX500Principal().getName())
                    .issuerPrincipal(issuer)
                    .subjectPrincipal(subject)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    static class TrustAllHttpsCertificates implements TrustManager, javax.net.ssl.X509TrustManager {


        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
