package debug;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FindMedianSortedArraysTest {

  /**
   * testing strategy:
   * a == null | b == null, a != null && b != null;
   * size(a + b) %2 == 0,size(a + b) %2 == 1;
   * size(a) < size(b),size(a) = size(b),size(a) > size(b)
   * max(a) <= max(b), min(a) >= min (b);max(a) > max(b), min(a) < min(b).
   */

  // 测试A为空，B不空；A和B数组大小和为偶数
  // 覆盖情况：a = null,b != null; size(a) + size(b) %2 == 0;size(a) < size(b)
  @Test void testArrayNullAndSumEven() {
    int[] a = null;
    int[] b = { 1, 2, 3, 4 };
    FindMedianSortedArrays findMedianSortedArrays =
        new FindMedianSortedArrays();
    double res = findMedianSortedArrays.findMedianSortedArrays(a, b);
    assertEquals(2.5, res);
  }

  // 测试A不空，B为空；A和B数组大小和为奇数；A大小小于B
  // 覆盖情况：a != null, b == null; size(a) + size(b) % 2 == 1; size(a) > size(b)
  @Test void testArrayNullAndSumOdd() {
    int[] b = null;
    int[] a = { 1, 2, 3 };
    FindMedianSortedArrays findMedianSortedArrays =
        new FindMedianSortedArrays();
    double res = findMedianSortedArrays.findMedianSortedArrays(a, b);
    assertEquals(2, res);
  }

  // 测试A和B非空，A和B大小和为偶数，A大小等于B，数组A最大值大于B最大值，最小值小于B最小值
  // 覆盖情况：a != null && b != null; size(a) + size(b) % 2 == 0; size(a) ==
  // size(b);
  // max(a) > max(b), min(a) < min(b)
  @Test void testNonnullAndSumEven() {
    int[] a = { 1, 2, 7 };
    int[] b = { 4, 5, 6 };
    FindMedianSortedArrays findMedianSortedArrays =
        new FindMedianSortedArrays();
    double res = findMedianSortedArrays.findMedianSortedArrays(a, b);
    assertEquals(4.5, res);
  }

  // 测试A和B非空，A和B大小和为奇数，A大小大于B，数组A最大值小于B最大值，最小值大于B最小值
  // 覆盖情况：a != null && b != null; size(a) + size(b) % 2 == 1; size(a) > size(b);
  // max(a) <= max(b), min(a) >= min (b);

  @Test void testNonnullAndSumOdd() {
    int[] a = { 1, 2, 3 };
    int[] b = { 0, 5 };
    FindMedianSortedArrays findMedianSortedArrays =
        new FindMedianSortedArrays();
    double res = findMedianSortedArrays.findMedianSortedArrays(a, b);
    assertEquals(2, res);
  }

}
