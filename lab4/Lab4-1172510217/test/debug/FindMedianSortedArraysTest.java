package debug;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FindMedianSortedArraysTest {

    /**
     * testing strategy:
     * A == null | B == null, A != null && B != null;
     * size(A + B) %2 == 0,size(A + B) %2 == 1;
     * size(A) < size(B),size(A) = size(B),size(A) > size(B)
     * max(A) <= max(B), min(A) >= min (B);max(A) > max(B), min(A) < min(B)
     */

    // 测试A为空，B不空；A和B数组大小和为偶数
    // 覆盖情况：A = null,B != null; size(A) + size(B) %2 == 0;size(A) < size(B)
    @Test void testArrayNullAndSumEven() {
        int[] A = null, B = { 1, 2, 3, 4 };
        FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();
        double res = findMedianSortedArrays.findMedianSortedArrays(A, B);
        assertEquals(2.5, res);
    }

    // 测试A不空，B为空；A和B数组大小和为奇数；A大小小于B
    // 覆盖情况：A != null, B == null; size(A) + size(B) % 2 == 1; size(A) > size(B)
    @Test void testArrayNullAndSumOdd() {
        int[] B = null, A = { 1, 2, 3 };
        FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();
        double res = findMedianSortedArrays.findMedianSortedArrays(A, B);
        assertEquals(2, res);
    }

    // 测试A和B非空，A和B大小和为偶数，A大小等于B，数组A最大值大于B最大值，最小值小于B最小值
    // 覆盖情况：A != null && B != null; size(A) + size(B) % 2 == 0; size(A) == size(B);
    // max(A) > max(B), min(A) < min(B)
    @Test void testNonnullAndSumEven() {
        int[] A = { 1, 2, 7 }, B = { 4, 5, 6 };
        FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();
        double res = findMedianSortedArrays.findMedianSortedArrays(A, B);
        assertEquals(4.5, res);
    }

    // 测试A和B非空，A和B大小和为奇数，A大小大于B，数组A最大值小于B最大值，最小值大于B最小值
    // 覆盖情况：A != null && B != null; size(A) + size(B) % 2 == 1; size(A) > size(B);
    // max(A) <= max(B), min(A) >= min (B);

    @Test void testNonnullAndSumOdd() {
        int[] A = { 1, 2, 3 }, B = { 0, 5 };
        FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();
        double res = findMedianSortedArrays.findMedianSortedArrays(A, B);
        assertEquals(2, res);
    }

}
