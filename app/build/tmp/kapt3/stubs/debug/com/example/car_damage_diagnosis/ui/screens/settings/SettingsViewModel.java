package com.example.car_damage_diagnosis.ui.screens.settings;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\n\u0010\u000e\u001a\u0004\u0018\u00010\u0007H\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0010J\u0006\u0010\u0012\u001a\u00020\u0010J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u0007H\u0002J\u000e\u0010\u0014\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u0007R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2 = {"Lcom/example/car_damage_diagnosis/ui/screens/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_user", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/car_damage_diagnosis/ui/screens/settings/User;", "user", "Lkotlinx/coroutines/flow/StateFlow;", "getUser", "()Lkotlinx/coroutines/flow/StateFlow;", "getFullPhoneNumber", "", "loadUserFromSharedPrefs", "onEditProfileClick", "", "onLogoutClick", "onThemeClick", "saveUserToSharedPrefs", "updateUser", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.car_damage_diagnosis.ui.screens.settings.User> _user = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.example.car_damage_diagnosis.ui.screens.settings.User> user = null;
    
    @javax.inject.Inject
    public SettingsViewModel(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.example.car_damage_diagnosis.ui.screens.settings.User> getUser() {
        return null;
    }
    
    public final void updateUser(@org.jetbrains.annotations.NotNull
    com.example.car_damage_diagnosis.ui.screens.settings.User user) {
    }
    
    private final com.example.car_damage_diagnosis.ui.screens.settings.User loadUserFromSharedPrefs() {
        return null;
    }
    
    private final void saveUserToSharedPrefs(com.example.car_damage_diagnosis.ui.screens.settings.User user) {
    }
    
    public final void onEditProfileClick() {
    }
    
    public final void onThemeClick() {
    }
    
    public final void onLogoutClick() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFullPhoneNumber() {
        return null;
    }
}