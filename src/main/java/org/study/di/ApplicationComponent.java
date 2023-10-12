package org.study.di;

public class ApplicationComponent {
//TODO this class will be creating objects of all other layers and return these
//TODO i cannot allow the use of foreign objects in layers (dara objects must be used only in data layer)

//    private static ApplicationComponent applicationComponent = null;
//
//    private ApplicationComponent() {    }
//
//    public static synchronized ApplicationComponent getInstance() {
//        if (applicationComponent == null) applicationComponent = new ApplicationComponent();
//
//        return applicationComponent;
//    }
//
//    private RecipeDataSource recipeDataSource;
//    private RecipeDataRepository recipeDataRepository;
//
//
//    public synchronized RecipeDataSource provideRecipeDataSource() {
//        return getRecipeDataSource();
//    }
//
//    public synchronized RecipeDataRepository provideDataRepository() {
//        return getRecipeDataRepository();
//    }
//
//
//    private RecipeDataSource getRecipeDataSource() {
//        if (recipeDataSource == null) {
//            recipeDataSource = new RecipeDataSource();
//        }
//
//        return recipeDataSource;
//    }
//
//    private RecipeDataRepository getRecipeDataRepository() {
//        if (recipeDataRepository == null) {
//            recipeDataRepository = new RecipeDataRepository(getRecipeDataSource());
//        }
//
//        return recipeDataRepository;
//    }

}