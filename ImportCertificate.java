import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ImportCertificate {

	private static final String URL = "https://repo.maven.apache.org/maven2/";

	private static final List<String> JVMS = Arrays
			.asList("C:\\Data\\dev\\java\\jdk1.8.0_221\\jre\\lib\\security\\cacerts");

	public static void main(String[] args)
			throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		URL url = new URL(URL);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sslsocketfactory);

		importCertificate(url);

	}

	private static void importCertificate(URL url) throws SSLPeerUnverifiedException, IOException,
			NoSuchAlgorithmException, CertificateException, KeyStoreException {

		SSLSocketFactory sslsocketfactory = disableSSLVerification();
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sslsocketfactory);

		conn.connect();

		Certificate[] cert = conn.getServerCertificates();

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

		char[] password = "changeit".toCharArray();

		for (String path : JVMS) {

			System.out.println("Loading certificates for: " + path);

			ks.load(new FileInputStream(path), password);
			
			ks.deleteEntry(url.getHost());

			ks.setCertificateEntry(url.getHost(), cert[0]);

			ks.store(new FileOutputStream(path), password);
		}
		System.out.println("Done!");

	}

	private static SSLSocketFactory disableSSLVerification() {

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				// do nothing
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				// do nothing
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			return sc.getSocketFactory();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		return (SSLSocketFactory) SSLSocketFactory.getDefault();
	}

}
