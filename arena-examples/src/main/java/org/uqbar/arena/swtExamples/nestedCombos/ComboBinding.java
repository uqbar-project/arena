package org.uqbar.arena.swtExamples.nestedCombos;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
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
import org.eclipse.swt.graphics.Rectangle;
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
 * Simplest binding. The source of data is a list of strings. 
 *
 * @author npasserini
 */
public class ComboBinding extends ApplicationWindow {
	public static void main(String[] args) {
		Display d = new Display();
		Realm.runWithDefault(SWTObservables.getRealm(d), new Runnable() {
			public void run() {
				try {
					ComboBinding wnd;
					wnd = new ComboBinding(null);
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
	private static final String SELECTED_PARTNUMBER = "SelectedPartNumber";
	private static final String SELECTED_TESTERS = "SelectedTesters";
	private static final String SERIALNUMBERS = "SerialNumbers";
	private static final String SELECTED_SERIALNUMBER = "SelectedSerialNumber";
	private static final String CREATE_PARAMFILE = "CreateParamfile";

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

	class PartNumber extends AbstractModelObject {
		private final String pnpref;
		private final String name;

		LinkedList<String> serialNumbers = new LinkedList<String>();
		String selectedSerial = "";
		boolean createParamFile;
		protected Binding binding;

		public PartNumber(String name) {
			this.name = name;
			pnpref = PREF + name + ".";
			serialNumbers.add(name + "_1");
			serialNumbers.add(name + "_2");
		}

		public String getName() {
			return name;
		}

		public String toString() {
			return "PN_" + name;
		}

		public LinkedList<String> getSerialNumbers() {
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

		public void load() {
			prefs.setDefault(pnpref + CREATE_PARAMFILE, true);

			selectedSerial = prefs.getString(pnpref + SELECTED_SERIALNUMBER);
			for (String sn : prefs.getString(pnpref + SERIALNUMBERS).split(";")) {
				if (serialNumbers.contains(sn))
					continue;
				serialNumbers.add(sn);
			}
			createParamFile = prefs.getBoolean(pnpref + CREATE_PARAMFILE);
		}

		public void save() {
			StringBuilder sns = new StringBuilder();
			if (selectedSerial != null && selectedSerial.length() > 0 && !serialNumbers.contains(selectedSerial)) {
				sns.append(selectedSerial);
			}
			for (String sn : serialNumbers) {
				if (sns.length() > 0)
					sns.append(";");
				sns.append(sn);
			}
			prefs.setValue(pnpref + SERIALNUMBERS, sns.toString());
			prefs.setValue(pnpref + SELECTED_SERIALNUMBER, selectedSerial == null ? "" : selectedSerial);
			prefs.setValue(pnpref + CREATE_PARAMFILE, createParamFile);
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

			testers = prefs.getString(PREF + SELECTED_TESTERS);
			String selectedPartNumberStr = prefs.getString(PREF + SELECTED_PARTNUMBER);

			for (String s : availablePartNumbers) {
				PartNumber pn = new PartNumber(s);
				partNumbers.add(pn);
				if (s.equals(selectedPartNumberStr)) {
					selectedPartNumber = pn;
				}
			}
			if (selectedPartNumber == null) {
				selectedPartNumber = partNumbers.getFirst();
			}
			for (PartNumber pn : partNumbers) {
				pn.load();
			}
		}

		public void save() {

			prefs.setValue(PREF + SELECTED_PARTNUMBER, selectedPartNumber.getName());
			prefs.setValue(PREF + SELECTED_TESTERS, testers);

			for (PartNumber pn : partNumbers) {
				pn.save();
			}

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

	public ComboBinding(Shell parentShell) throws Exception {
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
			BeansObservables.observeDetailList(Realm.getDefault(), selectedPn, "serialNumbers", String.class), null,
			null);

		bindingContext.bindValue(SWTObservables.observeText(cmbSerials.getCombo()),
			BeansObservables.observeDetailValue(Realm.getDefault(), selectedPn, "selectedSerial", String.class), null,
			null);

		bindingContext.bindValue(SWTObservables.observeSelection(btnCreateParamFile),
			BeansObservables.observeDetailValue(Realm.getDefault(), selectedPn, "createParamFile", Boolean.TYPE),
			null, null);

	}

	@Override
	public boolean close() {
		Rectangle bounds = getShell().getBounds();
		prefs.setValue(PREF + WND_SIZE_W, bounds.width);
		prefs.setValue(PREF + WND_SIZE_H, bounds.height);
		prefs.setValue(PREF + WND_POS_X, bounds.x);
		prefs.setValue(PREF + WND_POS_Y, bounds.y);

		int[] weights;
		weights = mainSashForm.getWeights();
		prefs.setValue(PREF + SF_WEIGTH_0, weights[0]);
		prefs.setValue(PREF + SF_WEIGTH_1, weights[1]);
		model.save();
		try {
			prefs.save(new FileOutputStream(file), "");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return super.close();
	}

}