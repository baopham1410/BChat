package com.example.appchat.model;

import com.example.appchat.view.viewfactory;

public class Model {
    private static Model model;

    public viewfactory getViewfactorys() {
        return viewfactorys;
    }

    private final viewfactory viewfactorys;

    public Model() {
        viewfactorys = new viewfactory();
    }

    public static synchronized Model getInstance() {
        if(model == null) {
            try {
                model = new Model();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return model;
    }
}
