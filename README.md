# Team 28
---

### Team Members

| **Roles**        | **Members** |
| ------------- |:-------------: |
| Product Owner | Uka, Fidan |
| Scrum Master  | Trummer, Viktoria |
| Developer | Langs, Thomas |
| Developer | Feldgrill, Lukas André |
| Developer | Grief, Sebastian |
| Developer | Leeb, Johannes Leonhard Günther |
| Developer | Nuñez Delgado, Jaime |
| Developer | Saf, Nikolas |
| Developer | Schweiger, Tamara |
| Developer | Seiser, Maria |
| Developer | Watko, Markus |

---

### Vision


The Hiker introduce a simple app that will be able to do several measurements for the user’s position and ambient, it will provide information about:
* Altitude of the user
* Speed of moving
* Humidity and temperature (Only for some versions of android)

Furthermore the user will be able to use features such:
* Psition on the map 
* Pedometer
* SOS message
* Dual language support (English & Russian)

In long terms The Hiker app tries to provide a useful app that will rise the awareness of the user regarding its environment and provides information about users ambient.

---

### Mockups

![MobileApp1](https://user-images.githubusercontent.com/79966516/114615879-7ec0c980-9ca6-11eb-85ac-ba26860b4a15.PNG)
![MobileApp2](https://user-images.githubusercontent.com/79966516/114615904-84b6aa80-9ca6-11eb-93ab-32ad477e2334.PNG)

---

### App

![Altitude](https://user-images.githubusercontent.com/79966516/121563247-84bef680-ca1a-11eb-9420-d7913ec80f70.jpg)
![FindMe](https://user-images.githubusercontent.com/79966516/121563251-85f02380-ca1a-11eb-831d-f9744035936d.jpg)
![Moving speed](https://user-images.githubusercontent.com/79966516/121563252-85f02380-ca1a-11eb-8df8-d4848315ae87.jpg)
![New main](https://user-images.githubusercontent.com/79966516/121563254-8688ba00-ca1a-11eb-9fdb-a3084fd95bff.jpg)
![Pedometer](https://user-images.githubusercontent.com/79966516/121563259-8688ba00-ca1a-11eb-8474-aabc0a033e25.jpg)
![SOS](https://user-images.githubusercontent.com/79966516/121563260-87215080-ca1a-11eb-8edf-b144ad764125.jpg)



---

### Remarks to test our App:

* Please enable developer settings
* Set this App as Mock Location Provider: Systems -> Advanced -> Developer Options -> Debugging -> Select Mock Location App 
* Set it to None afterwards for a correct behavior of the App

---

### Known Limitations for Release 1:

* On the real device the LanguageIntegrationTests are failing. But the App shows the correct selected language, also on the real device. So the tests are failing but the Apps language behaviour is correct. (In the Simulator everything works correct.)
* This issue will be handled in the next Release.

---

### Implemented features

* Altitude of the user
* Position on map
* Dual language support (English & Russian)
* Speed of moving
* Pedometer
* SOS message
* Humidity and Temperatrue
