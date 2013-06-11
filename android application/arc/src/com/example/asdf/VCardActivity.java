package com.example.asdf;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import net.sourceforge.cardme.vcard.VCardImpl;
import net.sourceforge.cardme.vcard.arch.VCardVersion;
import net.sourceforge.cardme.vcard.exceptions.VCardBuildException;
import net.sourceforge.cardme.vcard.features.AdrFeature;
import net.sourceforge.cardme.vcard.features.BeginFeature;
import net.sourceforge.cardme.vcard.features.EmailFeature;
import net.sourceforge.cardme.vcard.features.VersionFeature;
import net.sourceforge.cardme.vcard.types.AdrType;
import net.sourceforge.cardme.vcard.types.BeginType;
import net.sourceforge.cardme.vcard.types.EmailType;
import net.sourceforge.cardme.vcard.types.NameType;
import net.sourceforge.cardme.vcard.types.OrgType;
import net.sourceforge.cardme.vcard.types.TelType;
import net.sourceforge.cardme.vcard.types.UrlType;
import net.sourceforge.cardme.vcard.types.VersionType;
import net.sourceforge.cardme.vcard.types.params.AdrParamType;
import net.sourceforge.cardme.vcard.types.params.ExtendedParamType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class VCardActivity extends Activity {
	
	
	String mFileName;    
	
	Button crear;
	
	EditText pais;
	EditText ciudad;
	EditText localidad;
	EditText postal;
	EditText direccion;
	EditText nombre;
	EditText nombreFormateado;
	EditText mail;
	EditText organizacion;
	EditText web;
	EditText tfn;
	
	FileHelper fileHelper = new FileHelper();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vcard_layout);
		
		crear = (Button) findViewById(R.id.buttonCrear);
		
		pais = (EditText)findViewById(R.id.editTextPais);
		ciudad = (EditText)findViewById(R.id.editTextCuidad);
		localidad = (EditText)findViewById(R.id.editTextLocalidad);
		postal = (EditText)findViewById(R.id.editTextPostal);
		direccion = (EditText)findViewById(R.id.editTextDireccion);
		nombre = (EditText)findViewById(R.id.editTextNombre);
		mail = (EditText)findViewById(R.id.editTextEmail);
		web = (EditText)findViewById(R.id.editTextWeb);
		tfn = (EditText)findViewById(R.id.editTextTelefono);
		
		
		this.crear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	VCardImpl vcard = new VCardImpl();
                BeginType begin = new BeginType();
            	vcard.setBegin(begin);
            	vcard.setVersion((VersionType) new VersionType(VCardVersion.V3_0));

            	/*nombre*/
            	NameType name = new NameType();
            	String nombreString = nombre.getText().toString();
            	name.setName(nombreString);
            	

            	vcard.setName(name);
            	
            	AdrType address1 = new AdrType();
				
            	address1.setExtendedAddress("");
            	address1.setCountryName(pais.getText().toString());
            	address1.setLocality(localidad.getText().toString());
            	address1.setPostalCode(postal.getText().toString ());
            	address1.setStreetAddress(direccion.getText().toString());
            	
            	vcard.addAdr(address1);
            	
            	
         
            	/*email*/
            	
            	EmailType email = new EmailType();
            	email.setEmail(mail.getText().toString());
            	
            	

            	vcard.addEmail(email);
            	vcard.addEmail(new EmailType(mail.getText().toString()));
            	
            	/*organization*/
            	/*
            	OrgType organization = new OrgType();
            	//List<ExtendedParamType> xtendedParams = new List();
            	organization.addAllExtendedParams(xtendedParams);
            	organization.addOrgUnit(organizacion.getText().toString());
            	vcard.setOrg(organization);
            	*/
            	/*telephone*/
            	
            	TelType telephone = new TelType();
            	telephone.setTelephone(tfn.getText().toString());
            	vcard.addTel(telephone);
            	 
            
            	try {
					vcard.addUrl(new UrlType(new URL(web.getText().toString())));
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	
            	
            	 mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() +
     			"/ARC/"+ mail.getText().toString()+".vcf";
            	
            	 
            	try {
					fileHelper.saveVcardToFile(mFileName, vcard);
				} catch (VCardBuildException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	 
         		
            	
            	if(nombreString.compareTo("") == 0 || mail.getText().toString().compareTo("") == 0){
            		
            		Context context = getApplicationContext();
               	 	int duration = Toast.LENGTH_SHORT;
               	 	Toast toast = Toast.makeText(context, "Nombre y e-mail son campos obligatorios.", duration);
               	 	toast.show();
            		
            	}else{
            	
           	 	Intent returnIntent = new Intent();
           	 	returnIntent.putExtra("vCardRoute",mFileName);
           	 	setResult(RESULT_OK,returnIntent);     
           	 	finish();
            	}
            	
            }
      });
	}

	   	
	
  
}
