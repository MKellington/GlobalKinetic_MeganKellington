# GlobalKinetic_MeganKellington

GitHub Link: https://github.com/MKellington/GlobalKinetic_MeganKellington

How to Import

Click File > Import
Expand Git
Select Projects from Git(with smart import)
Click Next
Click Clone URI

Enter URI, User and password
URI:  https://github.com/MKellington/GlobalKinetic_MeganKellington.git
User: mkellington	
Password: Lithium9933

Follow the journey by clicking Next and or Finish


Pre-requisites
IDE: eclipse Release 4.7.0 (Oxygen)
jdk: jdk-14.0.1_windows-x64_bin
JAVA_VERSION: "14.0.1"
jar: selenium-server-standalone-3.141.59

Main goal is to have reusable code to prevent rework and cluttered scripts
Its a lot of work to setup but makes testing more efficent and uniform
Creates less work if maintenance is needed as you will only need to update the one section of code and not multiple methods/scripts

Tests and functions in folder src > gui
Common function holds methods that will be used by every script
-I find its cleaner and easier to have one place to store common methods to avoid duplication and long main scripts
-ie initialiseFunctions

Data function, same idea as common, holds info pertaining to reading from Excel and getting credentials from Environment.json

Utility function: Created methods like ClickObject and waitForProperty
- All my objects are stored in the xml files where i can specify the property of the object with out having to worry about which type as I have already catered for it 

Variables are stored in an exel spreadsheet
