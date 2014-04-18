package org.uqbar.arena.swtExamples.nestedCombos;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.uqbar.arena.tests.nestedCombos.Country;
import org.uqbar.arena.tests.nestedCombos.NestedCombosDomain;
import org.uqbar.arena.tests.nestedCombos.Province;

/**
 * Binding example using POO, without detail list. (Should we use detail list or not?)
 * 
 * @author npasserini
 */
public class ComboBinding4 extends ApplicationWindow {
	public static void main(String[] args) {
		Display d = new Display();
		Realm.runWithDefault(SWTObservables.getRealm(d), new Runnable() {
			public void run() {
				try {
					ComboBinding4 wnd;
					wnd = new ComboBinding4(null);
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
	private final PreferenceStore prefs;
	private SashForm mainSashForm;

	private NestedCombosDomain model;
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

	public ComboBinding4(Shell parentShell) throws Exception {
		super(parentShell);

		prefs = new PreferenceStore();
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
		newShell.setText(this.getClass().getSimpleName());
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

			cmbSerials = new ComboViewer(grp, SWT.DROP_DOWN);
			GridDataFactory.generate(cmbSerials.getCombo(), 2, 1);

		}

		mainSashForm.setWeights(new int[] { prefs.getInt(PREF + SF_WEIGTH_0), prefs.getInt(PREF + SF_WEIGTH_1) });

		this.model = new NestedCombosDomain();

		bindControls();

		lstPartNumbers.setInput(BeansObservables.observeList(Realm.getDefault(), model, "possibleCountries"));
		lstPartNumbers.getList().setFocus();

		// lstPartNumbers.setSelection(new StructuredSelection(model.getCountry()), true);
		return mainSashForm;
	}

	private void bindControls() {

		bindingContext = new DataBindingContext();

		HashSet<Country> countries = new HashSet<Country>(model.getPossibleCountries());
		System.out.println(countries);
		IObservableMap attributeMap = BeansObservables.observeMap(Observables.staticObservableSet(countries), Country.class, "name");
		lstPartNumbers.setContentProvider(new ArrayContentProvider());
		lstPartNumbers.setLabelProvider(new ObservableMapLabelProvider(attributeMap));

		bindingContext.bindList(SWTObservables.observeItems(cmbSerials.getCombo()),
			BeansObservables.observeList(Realm.getDefault(), this.model, "possibleProvinces", Province.class),
			new UpdateListStrategy().setConverter(new IConverter() {
				@Override
				public Object getToType() {
					return Province.class;
				}

				@Override
				public Object getFromType() {
					return String.class;
				}

				@Override
				public Object convert(Object fromObject) {
					return new Province((String) fromObject);
				}
			}), new UpdateListStrategy().setConverter(new IConverter() {
				@Override
				public Object getToType() {
					return String.class;
				}

				@Override
				public Object getFromType() {
					return Province.class;
				}

				@Override
				public Object convert(Object fromObject) {
					return ((Province) fromObject).getName();
				}
			}));

		bindingContext.bindValue(ViewersObservables.observeSingleSelection(lstPartNumbers),
			BeansObservables.observeValue(this.model, "country"), null,
			null);
	}

}