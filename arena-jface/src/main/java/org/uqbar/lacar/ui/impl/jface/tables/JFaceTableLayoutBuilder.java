package org.uqbar.lacar.ui.impl.jface.tables;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.uqbar.lacar.ui.impl.jface.builder.tables.JFaceTableBuilder;

import com.uqbar.commons.collections.CollectionFactory;

/**
 * Toma las columnas de la tabla y traduce su información de Layout a las necesidades de JFace, asignando
 * información de layout a las columnas a las que no se les haya realizado una indicación específica.
 * 
 * Para ello:
 * <ol>
 * <li>Las columnas que tienen un ancho fijo lo mantienen.
 * <li>Las demás se reparten en función de los pesos asignados, considerados como porcentajes del espacio
 * disponible <i><b>después</b></i> de restar el espacio necesario para las columnas de ancho fijo.
 * <li>La diferencia entre el peso total (100) y el peso asignado a las columnas se reparte entre las columnas
 * que no tengan información de layout explícita; tomando un mínimo de 5% para cada una de ellas.
 * </ol>
 * 
 * @author npasserini
 */
public class JFaceTableLayoutBuilder {
	private final JFaceTableBuilder<?> tableBuilder;

	/**
	 * Esta lista tiene <code>null</code>s en las posiciones de las columnas en las que el usuario no definió
	 * nada.
	 */
	private List<ColumnLayoutData> userDefinedLayouts = CollectionFactory.createList();

	/**
	 * El total de peso asignado en todos los layouts por peso. Se asume un total de peso de 100; la
	 * diferencia entre esta suma y ese total será repartida entre las columnas que no tengan un layout
	 * asignado.
	 */
	private int assignedWeight = 0;

	/**
	 * La cantidad de columnas sin un layout definido, entre todas estas columnas se repartirá el peso
	 * sobrante.
	 */
	private int columnsWithDefaultLayout = 0;

	public JFaceTableLayoutBuilder(JFaceTableBuilder<?> tableBuilder) {
		this.tableBuilder = tableBuilder;
	}

	// ********************************************************
	// ** Cálculo del layout definitivo
	// ********************************************************

	public TableLayout createLayout() {
		// Primera pasada: recabar la información provista por el usuario.
		for (JFaceColumnBuilder<?> column : this.tableBuilder.columns()) {
			column.getLayoutBuilder().configure(this);
		}

		// Segunda pasada: construir el layout de la tabla, repartiendo el peso restante entre las columnas
		// que no tienen un layout definido
		ColumnWeightData defaultColumnLayout = this.calculateDefaultLayout();

		TableLayout tableLayout = new TableLayout();
		for (ColumnLayoutData column : this.userDefinedLayouts) {
			tableLayout.addColumnData(column != null ? column : defaultColumnLayout);
		}

		return tableLayout;
	}

	private ColumnWeightData calculateDefaultLayout() {
		ColumnWeightData defaultColumnLayout = null;
		if (this.columnsWithDefaultLayout > 0) {
			defaultColumnLayout = new ColumnWeightData(this.getDefaultColumnWeight());
		}
		return defaultColumnLayout;
	}

	/**
	 * Se divide el peso restante (para llegar a 100) entre las columnas sin un peso asignado, pero tomando un
	 * mínimo de 5% para cada una de ellas.
	 */
	protected int getDefaultColumnWeight() {
		return Math.max((100 - this.assignedWeight) / this.columnsWithDefaultLayout, 5);
	}

	// ********************************************************
	// ** Métodos para agregar columnas
	// ********************************************************

	public void addFixedColumn(int pixels) {
		this.userDefinedLayouts.add(new ColumnPixelData(pixels));
	}

	public void addColumnWithWieght(int weight) {
		this.userDefinedLayouts.add(new ColumnWeightData(weight));
		this.assignedWeight += weight;
	}

	public void addColumnWithDefaultWeight() {
		this.userDefinedLayouts.add(null); // Placeholder para el layout que se asignará después.
		this.columnsWithDefaultLayout++;

	}

}
