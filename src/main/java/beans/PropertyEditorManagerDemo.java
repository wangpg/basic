package beans;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

import basic.anno.yuan.Person;

public class PropertyEditorManagerDemo {
	public static void main(String[] args) {
		PropertyEditor findEditor = PropertyEditorManager.findEditor(Person.class);
		findEditor.setValue("zs");
		System.out.println(findEditor.getAsText());
	}
}
