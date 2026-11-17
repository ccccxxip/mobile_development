package com.example.currentweatherdatabinding.databinding;
import com.example.currentweatherdatabinding.R;
import com.example.currentweatherdatabinding.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvTitle, 7);
        sViewsWithIds.put(R.id.spinnerCity, 8);
        sViewsWithIds.put(R.id.cardWeather, 9);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.Spinner) bindings[8]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[4]
            );
        this.main.setTag(null);
        this.tvCityName.setTag(null);
        this.tvCondition.setTag(null);
        this.tvHumidity.setTag(null);
        this.tvPressure.setTag(null);
        this.tvTemperature.setTag(null);
        this.tvWind.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.weather == variableId) {
            setWeather((com.example.currentweatherdatabinding.WeatherData) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setWeather(@Nullable com.example.currentweatherdatabinding.WeatherData Weather) {
        this.mWeather = Weather;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.weather);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String weatherCityName = null;
        java.lang.String weatherPressureFormatted = null;
        java.lang.String weatherTempFormatted = null;
        java.lang.String weatherHumidityFormatted = null;
        java.lang.String weatherCondition = null;
        java.lang.String weatherWindFormatted = null;
        com.example.currentweatherdatabinding.WeatherData weather = mWeather;

        if ((dirtyFlags & 0x3L) != 0) {



                if (weather != null) {
                    // read weather.cityName
                    weatherCityName = weather.getCityName();
                    // read weather.pressureFormatted
                    weatherPressureFormatted = weather.getPressureFormatted();
                    // read weather.tempFormatted
                    weatherTempFormatted = weather.getTempFormatted();
                    // read weather.humidityFormatted
                    weatherHumidityFormatted = weather.getHumidityFormatted();
                    // read weather.condition
                    weatherCondition = weather.getCondition();
                    // read weather.windFormatted
                    weatherWindFormatted = weather.getWindFormatted();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCityName, weatherCityName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCondition, weatherCondition);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvHumidity, weatherHumidityFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvPressure, weatherPressureFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTemperature, weatherTempFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvWind, weatherWindFormatted);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): weather
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}