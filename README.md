# JAVA FX TEMPLATE MENU
This is a simple structured javaFX menu template.  

## How does it work? 

1. Create an fxml view, with an AnchorPane as root.

3. Create a Controller for the view. ( do not link the controller in 
the fxml! This will later be done through data binding(3))

3. Create a new Enum attribute in View/ViewList.java, 
parse the fxml file name, controller name in the enum. EX

```JAVA
NEW("NEWView.fxml", "NewController");
```

4. Add a button in MainView.fxml and link it to the new view 
thrugh the MainController.java

