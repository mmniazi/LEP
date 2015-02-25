/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.beans.property.*;

/**
 * @author muhammad
 */
public class Strata {

    private final DoubleProperty height;
    private final DoubleProperty cohesion;
    private final DoubleProperty frictionAngle;
    private final DoubleProperty eUnityWeight;
    private final DoubleProperty kRankine;
    private final DoubleProperty kCoulomb;
    private final DoubleProperty k;
    private final IntegerProperty no;
    private final BooleanProperty saturated;
    private  double unitWeightWater;

    public Strata(int no) {
        this.saturated = new SimpleBooleanProperty();
        this.no = new SimpleIntegerProperty(no);
        this.height = new SimpleDoubleProperty();
        this.cohesion = new SimpleDoubleProperty();
        this.frictionAngle = new SimpleDoubleProperty();
        this.eUnityWeight = new SimpleDoubleProperty();
        this.kRankine = new SimpleDoubleProperty();
        this.kCoulomb = new SimpleDoubleProperty();
        this.k = new SimpleDoubleProperty();
    }

    public void seteUnityWeight(double eUnityWeight) {
        this.eUnityWeight.set(eUnityWeight);
    }

    public void setkRankine(double kRankine) {
        this.kRankine.set(kRankine);
    }

    public void setkCoulomb(double kCoulomb) {
        this.kCoulomb.set(kCoulomb);
    }

    public void setk(double k) {
        this.k.set(k);
    }

    public void setheight(double height) {
        this.height.set(height);
    }

    public void setfrictionAngle(double frictionAngle) {
        this.frictionAngle.set(frictionAngle);
    }

    public void setcohesion(double cohesion) {
        this.cohesion.set(cohesion);
    }

    public void setsaturated(boolean saturated) {
        this.saturated.set(saturated);
    }

    public void setUnitWeightWater(double unitWeightWater){
        this.unitWeightWater = unitWeightWater;
    }

    public double geteUnityWeight() {
        if (getsaturated()) {
            return eUnityWeight.get() - unitWeightWater;
        }
        return eUnityWeight.get();
    }

    public int getno() {
        return no.get();
    }

    public double getkRankine() {
        return kRankine.get();
    }

    public double getk() {
        return k.get();
    }

    public double getkCoulomb() {
        return kCoulomb.get();
    }

    public double getheight() {
        return height.get();
    }

    public double getfrictionAngle() {
        return frictionAngle.get() * 0.017453292;
    }

    public double getcohesion() {
        return cohesion.get();
    }

    public boolean getsaturated() {
        return saturated.get();
    }

    public DoubleProperty cohesionProperty() {
        return cohesion;
    }

    public DoubleProperty frictionAngleProperty() {
        return frictionAngle;
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public DoubleProperty kRankineProperty() {
        return kRankine;
    }

    public DoubleProperty kCoulombProperty() {
        return kCoulomb;
    }

    public DoubleProperty kProperty() {
        return k;
    }

    public IntegerProperty noProperty() {
        return no;
    }

    public DoubleProperty eUnityWeightProperty() {
        return eUnityWeight;
    }

    public BooleanProperty saturatedProperty() {
        return saturated;
    }
}
