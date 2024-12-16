package com.example.car_damage_diagnosis.ui.screens.settings;

import android.content.Context;
import androidx.compose.runtime.Composable;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001:\u0001)B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u001a\u001a\u00020\u000fJ\b\u0010\u001b\u001a\u00020\u0007H\u0002J\n\u0010\u001c\u001a\u0004\u0018\u00010\fH\u0002J\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u001eJ\u0006\u0010 \u001a\u00020\u001eJ\u0006\u0010!\u001a\u00020\u001eJ\u0006\u0010\"\u001a\u00020\u001eJ\u000e\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u0007J\u0010\u0010%\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u0007H\u0002J\u0010\u0010&\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\fH\u0002J\u0010\u0010\'\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\fH\u0002J\u000e\u0010(\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0019\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011\u00a8\u0006*"}, d2 = {"Lcom/example/car_damage_diagnosis/ui/screens/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_selectedTheme", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/car_damage_diagnosis/ui/screens/settings/SettingsViewModel$Theme;", "_showEditProfileDialog", "", "_showThemeDialog", "_user", "Lcom/example/car_damage_diagnosis/ui/screens/settings/User;", "formattedPhoneNumber", "Lkotlinx/coroutines/flow/StateFlow;", "", "getFormattedPhoneNumber", "()Lkotlinx/coroutines/flow/StateFlow;", "selectedTheme", "getSelectedTheme", "showEditProfileDialog", "getShowEditProfileDialog", "showThemeDialog", "getShowThemeDialog", "user", "getUser", "getFullPhoneNumber", "loadThemeFromSharedPrefs", "loadUserFromSharedPrefs", "onDismissEditProfileDialog", "", "onDismissThemeDialog", "onEditProfileClick", "onLogoutClick", "onThemeClick", "onThemeSelected", "theme", "saveThemeToSharedPrefs", "saveUserFromSharedPrefs", "saveUserToSharedPrefs", "updateUser", "Theme", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.car_damage_diagnosis.ui.screens.settings.User> _user = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.car_damage_diagnosis.ui.screens.settings.User> user = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> formattedPhoneNumber = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showEditProfileDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showEditProfileDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showThemeDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showThemeDialog = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.car_damage_diagnosis.ui.screens.settings.SettingsViewModel.Theme> _selectedTheme = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.car_damage_diagnosis.ui.screens.settings.SettingsViewModel.Theme> selectedTheme = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.car_damage_diagnosis.ui.screens.settings.User> getUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getFormattedPhoneNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowEditProfileDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowThemeDialog() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.car_damage_diagnosis.ui.screens.settings.SettingsViewModel.Theme> getSelectedTheme() {
        return null;
    }
    
    public final void updateUser(@org.jetbrains.annotations.NotNull()
    com.example.car_damage_diagnosis.ui.screens.settings.User user) {
    }
    
    public final void onEditProfileClick() {
    }
    
    public final void onDismissEditProfileDialog() {
    }
    
    public final void onThemeClick() {
    }
    
    public final void onDismissThemeDialog() {
    }
    
    public final void onThemeSelected(@org.jetbrains.annotations.NotNull()
    com.example.car_damage_diagnosis.ui.screens.settings.SettingsViewModel.Theme theme) {
    }
    
    private final com.example.car_damage_diagnosis.ui.screens.settings.User loadUserFromSharedPrefs() {
        return null;
    }
    
    private final void saveUserToSharedPrefs(com.example.car_damage_diagnosis.ui.screens.settings.User user) {
    }
    
    private final void saveUserFromSharedPrefs(com.example.car_damage_diagnosis.ui.screens.settings.User user) {
    }
    
    private final void saveThemeToSharedPrefs(com.example.car_damage_diagnosis.ui.screens.settings.SettingsViewModel.Theme theme) {
    }
    
    private final com.example.car_damage_diagnosis.ui.screens.settings.SettingsViewModel.Theme loadThemeFromSharedPrefs() {
        return null;
    }
    
    public final void onLogoutClick() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFullPhoneNumber() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/example/car_damage_diagnosis/ui/screens/settings/SettingsViewModel$Theme;", "", "(Ljava/lang/String;I)V", "Dark", "Light", "System", "app_debug"})
    public static enum Theme {
        /*public static final*/ Dark /* = new Dark() */,
        /*public static final*/ Light /* = new Light() */,
        /*public static final*/ System /* = new System() */;
        
        Theme() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.example.car_damage_diagnosis.ui.screens.settings.SettingsViewModel.Theme> getEntries() {
            return null;
        }
    }
}