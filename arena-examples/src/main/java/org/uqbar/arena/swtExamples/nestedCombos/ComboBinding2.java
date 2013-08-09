package org.uqbar.arena.swtExamples.nestedCombos;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.AbstractObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * A combo binding where the source of data is a list of SerialNumbers. In order to fill the combo a
 * Transformer converts from SerialNumber to String.
 * 
 * @author npasserini
 */
public class ComboBinding2 extends ApplicationWindow {
	public static void main(String[] args) {
		Display d = new Display();
		Realm.runWithDefault(SWTObservables.getRealm(d), new Runnable() {
			public void run() {
				try {
					ComboBinding2 wnd;
					wnd = new ComboBinding2(null);
					wnd.setBlockOnOpen(true);
					wnd.open();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static final String PREF = "DlgEolStarter.";

	private static final String RIGHT_SF_WEIGTH_1 = "RightSF.Weigth.1";
	private static final String RIGHT_SF_WEIGTH_0 = "RightSF.Weigth.0";
	private static final String SF_WEIGTH_1 = "SF.Weigth.1";
	private static final String SF_WEIGTH_0 = "SF.Weigth.0";
	private static final String WND_POS_Y = "Wnd.Pos.Y";
	private static final String WND_POS_X = "Wnd.Pos.X";
	private static final String WND_SIZE_H = "Wnd.Size.H";
	private static final String WND_SIZE_W = "Wnd.Size.W";

	private DataBindingContext bindingContext;
	private final File file;

	private final PreferenceStore prefs;
	private SashForm mainSashForm;

	private Model model;
	private Button btnCreateParamFile;
	private ComboViewer cmbSerials;

	private ListViewer lstPartNumbers;

	public static abstract class AbstractModelObject {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(listener);
		}

		public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeSupport.removePropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
		}

		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	class SerialNumber extends AbstractModelObject {
		private final String number;

		public SerialNumber(String number) {
			this.number = number;
		}

		public Object getNumber() {
			return this.number;
		}

	}

	class PartNumber extends AbstractModelObject {
		private final String name;

		LinkedList<SerialNumber> serialNumbers = new LinkedList<SerialNumber>();
		String selectedSerial = "";
		boolean createParamFile;
		protected Binding binding;

		public PartNumber(String name) {
			this.name = name;
			serialNumbers.add(new SerialNumber(name + "_1"));
			serialNumbers.add(new SerialNumber(name + "_2"));
		}

		public String getName() {
			return name;
		}

		public String toString() {
			return "PN_" + name;
		}

		public LinkedList<SerialNumber> getSerialNumbers() {
			System.out.printf("PN %s getSerialNumbers\n", name);
			return serialNumbers;
		}

		public String getSelectedSerial() {
			System.out.printf("PN %s getSelectedSerial %s\n", name, selectedSerial);
			return selectedSerial;
		}

		public void setSelectedSerial(String selectedSerial) {
			System.out.printf("PN %s setSelectedSerial %s\n", name, selectedSerial);
			this.selectedSerial = selectedSerial;
			firePropertyChange("selectedSerial", null, null);
		}

		public boolean getCreateParamFile() {
			return createParamFile;
		}

		public void setCreateParamFile(boolean createParamFile) {
			boolean old = this.createParamFile;
			this.createParamFile = createParamFile;
			firePropertyChange("createParamFile", old, createParamFile);
		}

	}

	class Model extends AbstractObservableList {
		String testers;
		LinkedList<PartNumber> partNumbers = new LinkedList<PartNumber>();
		PartNumber selectedPartNumber;

		public String getTesters() {
			return testers;
		}

		public void setTesters(String testers) {
			this.testers = testers;
		}

		public PartNumber getSelectedPartNumber() {
			return selectedPartNumber;
		}

		public void setSelectedPartNumber(PartNumber pn) {
			selectedPartNumber = pn;
		}

		public void load(String[] availablePartNumbers) {
			for (String s : availablePartNumbers) {
				partNumbers.add(new PartNumber(s));
			}

			selectedPartNumber = partNumbers.getFirst();
		}

		@Override
		public Object getElementType() {
			return PartNumber.class;
		}

		@Override
		protected int doGetSize() {
			return partNumbers.size();
		}

		@Override
		public Object get(int index) {
			return partNumbers.get(index);
		}

		public Collection<PartNumber> getPartNumbers() {
			return partNumbers;
		}

	}

	public ComboBinding2(Shell parentShell) throws Exception {
		super(parentShell);

		prefs = new PreferenceStore();
		file = new File("prefs.ini");
		if (file.canRead()) {
			prefs.load(new FileInputStream(file));
		}
		prefs.setDefault(PREF + WND_SIZE_W, 600);
		prefs.setDefault(PREF + WND_SIZE_H, 500);
		prefs.setDefault(PREF + WND_POS_X, 100);
		prefs.setDefault(PREF + WND_POS_Y, 100);

		prefs.setDefault(PREF + SF_WEIGTH_0, 50);
		prefs.setDefault(PREF + SF_WEIGTH_1, 50);
		prefs.setDefault(PREF + RIGHT_SF_WEIGTH_0, 25);
		prefs.setDefault(PREF + RIGHT_SF_WEIGTH_1, 75);

	}

	@Override
	protected Point getInitialSize() {
		return new Point(prefs.getInt(PREF + WND_SIZE_W), prefs.getInt(PREF + WND_SIZE_H));
	}

	@Override
	protected Point getInitialLocation(Point initialSize) {
		return new Point(prefs.getInt(PREF + WND_POS_X), prefs.getInt(PREF + WND_POS_Y));
	}

	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText("ComboBinding");
		super.configureShell(newShell);
	}

	@Override
	protected Control createContents(Composite parent) {

		mainSashForm = new SashForm(parent, SWT.HORIZONTAL);
		mainSashForm.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		lstPartNumbers = new ListViewer(mainSashForm, SWT.BORDER);

		{
			Composite comp = new Composite(mainSashForm, SWT.NONE);
			comp.setLayout(new GridLayout(2, false));

			new Label(comp, SWT.NONE).setText("Tester:");

			Group grp = new Group(comp, SWT.NONE);
			grp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1));
			grp.setText("Options");
			grp.setLayout(new GridLayout(2, false));
			Button btn;

			btn = btnCreateParamFile = new Button(grp, SWT.CHECK);
			btn.setText("Create Parameter File");
			GridDataFactory.generate(btn, 2, 1);

			cmbSerials = new ComboViewer(grp, SWT.DROP_DOWN);
			GridDataFactory.generate(cmbSerials.getCombo(), 2, 1);

		}

		mainSashForm.setWeights(new int[] { prefs.getInt(PREF + SF_WEIGTH_0), prefs.getInt(PREF + SF_WEIGTH_1) });

		this.model = new Model();
		String partNumbers[] = new String[] { "AAA", "123" };
		model.load(partNumbers);

		bindControls();

		lstPartNumbers.setInput(model);
		lstPartNumbers.getList().setFocus();

		lstPartNumbers.setSelection(new StructuredSelection(model.getSelectedPartNumber()), true);
		return mainSashForm;
	}

	private void bindControls() {

		bindingContext = new DataBindingContext();

		IObservableMap attributeMap = BeansObservables.observeMap(
			Observables.staticObservableSet(new HashSet<PartNumber>(model.getPartNumbers())), PartNumber.class, "name");
		lstPartNumbers.setContentProvider(new ArrayContentProvider());
		lstPartNumbers.setLabelProvider(new ObservableMapLabelProvider(attributeMap));

		IObservableValue selectedPn = ViewersObservables.observeSingleSelection(lstPartNumbers);

		bindingContext.bindList(SWTObservables.observeItems(cmbSerials.getCombo()),
			BeansObservables.observeDetailList(Realm.getDefault(), selectedPn, "serialNumbers", SerialNumber.class),
			new UpdateListStrategy().setConverter(new IConverter() {
				@Override
				public Object getToType() {
					return SerialNumber.class;
				}

				@Override
				public Object getFromType() {
					return String.class;
				}

				@Override
				public Object convert(Object fromObject) {
					return new SerialNumber((String) fromObject);
				}
			}), new UpdateListStrategy().setConverter(new IConverter() {
				@Override
				public Object getToType() {
					return String.class;
				}

				@Override
				public Object getFromType() {
					return SerialNumber.class;
				}

				@Override
				public Object convert(Object fromObject) {
					return ((SerialNumber) fromObject).getNumber();
				}
			}));

		bindingContext.bindValue(SWTObservables.observeText(cmbSerials.getCombo()),
			BeansObservables.observeDetailValue(Realm.getDefault(), selectedPn, "selectedSerial", String.class), null,
			null);

		bindingContext.bindValue(SWTObservables.observeSelection(btnCreateParamFile),
			BeansObservables.observeDetailValue(Realm.getDefault(), selectedPn, "createParamFile", Boolean.TYPE), null,
			null);

	}
}