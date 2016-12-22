public class BinarySearch<E extends Comparable<E>> {

	/**
	 * Binary Search to find the first occurrence of a value in the given array
	 * 
	 * @param array
	 *            sorted array on which the search has to be performed
	 * @param val
	 *            the key which has to be searched for
	 * @return pos if key exists or -pos if the key doesn't exist but where it
	 *         would've been inserted in this array
	 */
	public int firstOccurenceBinarySearch(E[] array, E val) {
		int len = array == null ? 0 : array.length;
		if (len == 0) {
			return -1;
		}
		int low = 0, high = len - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			if (array[mid].compareTo(val) <= 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return array[low].compareTo(val) == 0 ? low : -low;
	}

	/**
	 * Binary Search to find the last occurrence of a value in the given array
	 * 
	 * @param array
	 *            sorted array on which the search has to be performed
	 * @param val
	 *            the key which has to be searched for
	 * @return pos if key exists or -pos if the key doesn't exist but where it
	 *         would've been inserted in this array
	 */
	public int lastOccurrenceBinarySearch(E[] array, E val) {
		int len = array == null ? 0 : array.length;
		if (len == 0) {
			return -1;
		}
		int low = 0, high = len - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			if (array[mid].compareTo(val) < 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return array[low].compareTo(val) == 0 ? low : -low;
	}

	/**
	 * Binary Search to find if a value is present in the given sorted array
	 * 
	 * @param array
	 *            sorted array on which the search has to be performed
	 * @param val
	 *            the key which has to be searched for
	 * @return pos if key exists or -1 if the key doesn't exist in this array
	 */
	public int binarySearch(E[] array, E val) {
		int len = array == null ? 0 : array.length;
		if (len == 0) {
			return -1;
		}
		int low = 0, high = len - 1;
		while (low <= high) {
			int mid = low + ((high - low) >> 1);
			int compare = array[mid].compareTo(val);
			if (compare < 0) {
				high = mid - 1;
			} else if (compare > 0) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

}
