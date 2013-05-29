package com.example.asdf;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import info.ineighborhood.cardme.vcard.VCardImpl;
import info.ineighborhood.cardme.vcard.VCardVersion;
import info.ineighborhood.cardme.vcard.features.AddressFeature;
import info.ineighborhood.cardme.vcard.features.EmailFeature;
import info.ineighborhood.cardme.vcard.features.OrganizationFeature;
import info.ineighborhood.cardme.vcard.features.TelephoneFeature;
import info.ineighborhood.cardme.vcard.features.URLFeature;
import info.ineighborhood.cardme.vcard.features.VersionFeature;
import info.ineighborhood.cardme.vcard.types.AddressType;
import info.ineighborhood.cardme.vcard.types.BirthdayType;
import info.ineighborhood.cardme.vcard.types.EmailType;
import info.ineighborhood.cardme.vcard.types.FormattedNameType;
import info.ineighborhood.cardme.vcard.types.NameType;
import info.ineighborhood.cardme.vcard.types.OrganizationType;
import info.ineighborhood.cardme.vcard.types.TelephoneType;
import info.ineighborhood.cardme.vcard.types.URLType;
import info.ineighborhood.cardme.vcard.types.VersionType;
import info.ineighborhood.cardme.vcard.types.parameters.AddressParameterType;
import info.ineighborhood.cardme.vcard.types.parameters.EmailParameterType;
import info.ineighborhood.cardme.vcard.types.parameters.ParameterTypeStyle;
import info.ineighborhood.cardme.vcard.types.parameters.TelephoneParameterType;
import info.ineighborhood.cardme.vcard.types.parameters.XAddressParameterType;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class VCardActivity extends Activity {
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vcard_layout);
		
		crear = (Button) findViewById(R.id.buttonCrear);
		
		this.crear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	VCardImpl vcard = new VCardImpl();
            	vcard.setVersion((VersionFeature) new VersionType(VCardVersion.V3_0));

            	/*nombre*/
            	NameType name = new NameType();
            	name.setGivenName(nombre.getText().toString());
            	

            	vcard.setName(name);
            	vcard.setFormattedName(new FormattedNameType());
            	/*direccion*/
            	
            	AddressFeature address1 = new AddressType();
            	address1.setExtendedAddress("");
            	address1.setCountryName(pais.getText().toString());
            	address1.setLocality(localidad.getText().toString());
            	address1.setPostalCode(postal.getText().toString ());
            	address1.setStreetAddress(direccion.getText().toString());
            	address1.addAddressParameterType(AddressParameterType.HOME);
            	address1.addAddressParameterType(AddressParameterType.PARCEL);
            	address1.addAddressParameterType(AddressParameterType.PREF);
            	address1.addExtendedAddressParameterType(new XAddressParameterType("CUSTOM-PARAM-TYPE"));
            	address1.addExtendedAddressParameterType(new XAddressParameterType("CUSTOM-PARAM-TYPE", "WITH-CUSTOM-VALUE"));

            	vcard.addAddress(address1);
            	
            	/*nacimiento*/
            	
            	Calendar birthday = Calendar.getInstance();
            	birthday.clear();//change for spinner
            	birthday.set(Calendar.YEAR, 1980);
            	birthday.set(Calendar.MONTH, 4);
            	birthday.set(Calendar.DAY_OF_MONTH, 21);

            	vcard.setBirthday(new BirthdayType(birthday));

            	/*email*/
            	
            	EmailFeature email = new EmailType();
            	email.setEmail(mail.getText().toString());
            	email.addEmailParameterType(EmailParameterType.IBMMAIL);
            	email.addEmailParameterType(EmailParameterType.INTERNET);
            	email.addEmailParameterType(EmailParameterType.PREF);

            	vcard.addEmail(email);
            	vcard.addEmail(new EmailType(mail.getText().toString()));
            	
            	/*organization*/
            	
            	OrganizationFeature organizations = new OrganizationType();
            	organizations.addOrganization(organizacion.getText().toString());
            	vcard.setOrganizations(organizations);
            	
            	/*telephone*/
            	
            	TelephoneFeature telephone = new TelephoneType();
            	telephone.setTelephone(tfn.getText().toString());
            	telephone.addTelephoneParameterType(TelephoneParameterType.CELL);
            	telephone.addTelephoneParameterType(TelephoneParameterType.HOME);
            	telephone.setParameterTypeStyle(ParameterTypeStyle.PARAMETER_VALUE_LIST);
            	vcard.addTelephoneNumber(telephone);
            	 
            	/*url*/
            	
            	try {
					vcard.addURL((URLFeature) new URLType(new URL(web.getText().toString())));
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            }
      });
	}


	
	
  
}
