package ru.job4j.pool;

import java.util.Objects;

public class Sums {
    private final int rowSum;
    private final int colSum;

    public Sums(int row, int col) {
        this.rowSum = row;
        this.colSum = col;
    }

    public int getRowSum() {
        return rowSum;
    }

    public int getColSum() {
        return colSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Sums sums = (Sums) o;
        return getRowSum() == sums.getRowSum() && getColSum() == sums.getColSum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRowSum(), getColSum());
    }

    @Override
    public String toString() {
        return "Sums{"
                + "rowSum=" + rowSum
                + ", colSum=" + colSum
                + '}';
    }
}
