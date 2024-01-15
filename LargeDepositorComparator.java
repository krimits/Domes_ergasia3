import java.util.Comparator;

public class LargeDepositorComparator implements Comparator<LargeDepositor> {
    @Override
    public int compare(LargeDepositor o1, LargeDepositor o2) {
        double LargeDepositor1 = o1.getSavings() - o1.getTaxedIncomes();
        double LargeDepositor2 = o2.getSavings() - o2.getTaxedIncomes();
        double compareDepositors = LargeDepositor1 - LargeDepositor2;

        if (o1.getTaxedIncomes() < 8000) {
            if (LargeDepositor1 < LargeDepositor2){
                return -1;
            }
            else if (LargeDepositor1 > LargeDepositor2){
                return 1;
            }
            else {
                return 0;
            }
        }
        else {
            if (compareDepositors < 0){
                return -1;
            }
            else if (compareDepositors > 0){
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}
