package com.nec.lmp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.signatures.ExternalBlankSignatureContainer;
import com.itextpdf.signatures.IExternalSignatureContainer;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;

public class EsignPdf {

public void sign() throws Exception {
		
				String contents ="";
				String tempDocPath = "ORG1_temp5.pdf";
				String signedDocPath = "ORG1_SIGNED.pdf";
				String certPath ="abc.crt";

				Path tempDocPathObj = Paths.get(tempDocPath, "ORG1_unsigned.pdf");
				Path signedDocPathObj = Paths.get(signedDocPath, "ORG1_unsigned.pdf");
				Path certPathObj = Paths.get(certPath);

				System.out.println("certPathObj=" + certPathObj.toString());
				FileInputStream is = new FileInputStream(certPathObj.toString());
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				X509Certificate cert = (X509Certificate) cf.generateCertificate(is);

				int noofPages = 1;
				PdfReader reader = new PdfReader("ORG1_unsigned.pdf"); // Unsigned
																					// pdf
																					// file
				PdfSigner signer = new PdfSigner(reader, new FileOutputStream(tempDocPathObj.toString()), true); // temp
																													// file
				// PdfDocument pdfDoc = new PdfDocument(reader);
				PdfSignatureAppearance appearance = signer.getSignatureAppearance();
				appearance.setPageRect(new Rectangle(300, 200, 300, 200)).setPageNumber(noofPages).setCertificate(cert);
				signer.setFieldName("Testing");
				appearance.setCertificate(cert);
				appearance.setReason("Security");


				/*
				 * ExternalBlankSignatureContainer constructor will create the PdfDictionary for
				 * the signature information and will insert the /Filter and /SubFilter values
				 * into this dictionary. It will leave just a blank placeholder for the
				 * signature that is to be inserted later.
				 */
				IExternalSignatureContainer external = new ExternalBlankSignatureContainer(PdfName.Adobe_PPKLite,
						PdfName.Adbe_pkcs7_detached);

				// Sign the document using an external container.
				// 8192 is the size of the empty signature placeholder.
				signer.signExternalContainer(external, 8192);

				PdfReader readernew = new PdfReader(tempDocPathObj.toString());
				try (FileOutputStream os = new FileOutputStream(signedDocPathObj.toString())) {
					PdfSigner signernew = new PdfSigner(readernew, os, true);

					IExternalSignatureContainer externalnew = new PExternalSignatureContainer((contents.getBytes()));

					// Signs a PDF where space was already reserved. The field
					// must cover the whole
					// document.
					signernew.signDeferred(signernew.getDocument(), "Testing", os, externalnew);
				}

				File f = new File(signedDocPathObj.toString());
				boolean flag = f.setReadOnly();

			}
		
}

