package examples.datastructure.list;

import java.util.*;

public class SimpleList {
		private List<Integer> list;

		public SimpleList() {
			this.list = new ArrayList<>();
		}

		public void add(int value) {
			list.add(value);
		}

		public boolean remove(int value) {
			return list.remove(Integer.valueOf(value));
		}

		public Integer get(int index) {
			return list.get(index);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			SimpleList that = (SimpleList) obj;
			return Objects.equals(list, that.list);
		}

		@Override
		public int hashCode() {
			return Objects.hash(list);
		}

		public boolean isSorted() {
			if (list.isEmpty() || list.size() == 1) return true;

			for (int i = 0; i < list.size() - 1; i++) {
				if (list.get(i) > list.get(i + 1)) {
					return false;
				}
			}
			return true;
		}

		@Override
		public String toString() {
			return list.toString();
		}

		public Collection<Integer> toCollection() {
			return list;
		}

		public Integer size(){
			return list.size();
		}
}
