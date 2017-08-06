package at.korti.transmatrics.util.helper;

/**
 * Created by Korti on 06.08.2017.
 */
public class EnergyUnitHelper {

    public static String fromatEnergy(int energy, boolean withUnit) {
        return getUnit(energy).formatEnergy(energy, withUnit);
    }

    public static boolean hasTheSameUnit(int energy1, int energy2) {
        return getUnit(energy1).equals(getUnit(energy2));
    }

    private static EnergyUnit getUnit(int energy) {
        if(energy >= Math.pow(10, 9)) {              // Giga
            return new EnergyUnit("GRF", 9);
        } else if (energy >= Math.pow(10, 6)) {      // Mega
            return new EnergyUnit("MRF", 6);
        } else if (energy >= Math.pow(10, 3)) {      // Kilo
            return new EnergyUnit("KRF", 3);
        }
        return new EnergyUnit("RF", 0);
    }

    private static class EnergyUnit {
        private String unit;
        private double powerOfTen;

        public EnergyUnit(String unit, int powerOfTen) {
            this.unit = unit;
            this.powerOfTen = Math.pow(10, powerOfTen);
        }

        public double calcEnergy(int energy) {
            if(powerOfTen != 0) {
                return ((double) energy) / powerOfTen;
            }
            return energy;
        }

        public String getUnit() {
            return this.unit;
        }

        public String formatEnergy(int energy, boolean withUnit) {
            if(withUnit) {
                return String.format("%.2f %s", calcEnergy(energy), getUnit());
            } else {
                return String.format("%.2f", calcEnergy(energy));
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EnergyUnit that = (EnergyUnit) o;

            if (Double.compare(that.powerOfTen, powerOfTen) != 0) return false;
            return unit != null ? unit.equals(that.unit) : that.unit == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = unit != null ? unit.hashCode() : 0;
            temp = Double.doubleToLongBits(powerOfTen);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

}
