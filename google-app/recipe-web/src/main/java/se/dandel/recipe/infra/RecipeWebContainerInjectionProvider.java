package se.dandel.recipe.infra;

import se.dandel.recipe.web.infra.guice.RecipeModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.vendor.WebContainerInjectionProvider;

public class RecipeWebContainerInjectionProvider implements InjectionProvider {

    private WebContainerInjectionProvider internalProvider = new WebContainerInjectionProvider();
    private Injector injector = Guice.createInjector(new RecipeModule());

    @Override
    public void inject(Object managedBean) throws InjectionProviderException {
        internalProvider.inject(managedBean);
        injector.injectMembers(managedBean);
    }

    @Override
    public void invokePreDestroy(Object managedBean) throws InjectionProviderException {
        internalProvider.invokePreDestroy(managedBean);
    }

    @Override
    public void invokePostConstruct(Object managedBean) throws InjectionProviderException {
        internalProvider.invokePostConstruct(managedBean);
    }

}
