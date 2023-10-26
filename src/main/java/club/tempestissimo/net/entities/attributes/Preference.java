package club.tempestissimo.net.entities.attributes;

public class Preference {
    private double densityPreference;
    private double reciprocityPreference;
    private double popularityPreference;
    private double activityPreference;
    private double transitivityPreference;
    private double indirectRelationEffectPreference;
    private double indirectRelationEffectPreferenceIsWeakConnectionThreshold;
    private double balancePreference;
    private double balancePreferenceB0;

    public Preference(double densityPreference, double reciprocityPreference, double popularityPreference, double activityPreference, double transitivityPreference, double indirectRelationEffectPreference, double indirectRelationEffectPreferenceIsWeakConnectionThreshold, double balancePreference, double balancePreferenceB0) {
        this.densityPreference = densityPreference;
        this.reciprocityPreference = reciprocityPreference;
        this.popularityPreference = popularityPreference;
        this.activityPreference = activityPreference;
        this.transitivityPreference = transitivityPreference;
        this.indirectRelationEffectPreference = indirectRelationEffectPreference;
        this.indirectRelationEffectPreferenceIsWeakConnectionThreshold = indirectRelationEffectPreferenceIsWeakConnectionThreshold;
        this.balancePreference = balancePreference;
        this.balancePreferenceB0 = balancePreferenceB0;
    }

    public double getDensityPreference() {
        return densityPreference;
    }

    public void setDensityPreference(double densityPreference) {
        this.densityPreference = densityPreference;
    }

    public double getReciprocityPreference() {
        return reciprocityPreference;
    }

    public void setReciprocityPreference(double reciprocityPreference) {
        this.reciprocityPreference = reciprocityPreference;
    }

    public double getPopularityPreference() {
        return popularityPreference;
    }

    public void setPopularityPreference(double popularityPreference) {
        this.popularityPreference = popularityPreference;
    }

    public double getActivityPreference() {
        return activityPreference;
    }

    public void setActivityPreference(double activityPreference) {
        this.activityPreference = activityPreference;
    }

    public double getTransitivityPreference() {
        return transitivityPreference;
    }

    public void setTransitivityPreference(double transitivityPreference) {
        this.transitivityPreference = transitivityPreference;
    }

    public double getIndirectRelationEffectPreference() {
        return indirectRelationEffectPreference;
    }

    public void setIndirectRelationEffectPreference(double indirectRelationEffectPreference) {
        this.indirectRelationEffectPreference = indirectRelationEffectPreference;
    }

    public double getBalancePreference() {
        return balancePreference;
    }

    public void setBalancePreference(double balancePreference) {
        this.balancePreference = balancePreference;
    }

    public double getBalancePreferenceB0() {
        return balancePreferenceB0;
    }

    public void setBalancePreferenceB0(double balancePreferenceB0) {
        this.balancePreferenceB0 = balancePreferenceB0;
    }

    public double getIndirectRelationEffectPreferenceIsWeakConnectionThreshold() {
        return indirectRelationEffectPreferenceIsWeakConnectionThreshold;
    }

    public void setIndirectRelationEffectPreferenceIsWeakConnectionThreshold(double indirectRelationEffectPreferenceIsWeakConnectionThreshold) {
        this.indirectRelationEffectPreferenceIsWeakConnectionThreshold = indirectRelationEffectPreferenceIsWeakConnectionThreshold;
    }

    public Preference copy(){
        return new Preference(this.densityPreference,this.reciprocityPreference,this.popularityPreference,this.activityPreference,this.transitivityPreference,this.indirectRelationEffectPreference,this.indirectRelationEffectPreferenceIsWeakConnectionThreshold,this.balancePreference,this.balancePreferenceB0);
    }

    @Override
    public String toString() {
        return "" +
                "densityPreference=" + densityPreference +
                ";\nreciprocityPreference=" + reciprocityPreference +
                ";\npopularityPreference=" + popularityPreference +
                ";\nactivityPreference=" + activityPreference +
                ";\ntransitivityPreference=" + transitivityPreference +
                ";\nindirectRelationEffectPreference=" + indirectRelationEffectPreference +
                ";\nindirectRelationEffectPreferenceIsWeakConnectionThreshold=" + indirectRelationEffectPreferenceIsWeakConnectionThreshold +
                ";\nbalancePreference=" + balancePreference +
                ";\nbalancePreferenceB0=" + balancePreferenceB0 +
                ";\n";
    }
}
