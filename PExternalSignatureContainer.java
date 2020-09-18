package com.nec.lmp;

import java.io.InputStream;
import java.security.GeneralSecurityException;

import com.itextpdf.signatures.IExternalSignatureContainer;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.security.ExternalSignatureContainer;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;

public class PExternalSignatureContainer implements  IExternalSignatureContainer {
    protected byte[] sig;
    private PdfPKCS7 sigField;

    public PExternalSignatureContainer(byte[] sig) {
        this.sig = sig;
     
    }


	@Override
	public byte[] sign(InputStream data) throws GeneralSecurityException {
	
		
		return sig;
		
	}



	@Override
	public void modifySigningDictionary(com.itextpdf.kernel.pdf.PdfDictionary signDic) {
		// TODO Auto-generated method stub
		
	}
}
