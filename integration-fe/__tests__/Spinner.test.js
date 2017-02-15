import React from 'react';
import { shallow } from 'enzyme';
import Spinner from '../src/components/Spinner/Spinner.jsx';

function setup() {
  const props = {
    loading: true,
    loadingMessage: 'Searching for Instances',
  }

  const enzymeWrapper = shallow(<Spinner {...props} />);

  return {
    props,
    enzymeWrapper,
  }
}

describe('components', () => {
  describe('Spinner', () => {

    it('Should show loading message when state loading is true!', () => {
      const { enzymeWrapper } = setup();
      expect(enzymeWrapper.find('p').text()).not.toBeNull();
    });

    it('Should NOT have the class "spinner-opacity-0" when loading is true!', () => {
      const { enzymeWrapper } = setup();
      expect(enzymeWrapper.find('div').hasClass('spinner')).toBe(true);
    });

    it('Should NOT have the class "spinner-opacity-0" when loading is true!', () => {
      const { enzymeWrapper } = setup();
      expect(enzymeWrapper.find('div').hasClass('spinner-opacity-0')).toBe(false);
    });

    it('Should have the class "spinner-opacity-0" when loading is false!', () => {
      let { props, enzymeWrapper } = setup();
      props.loading = false;
      enzymeWrapper = shallow(<Spinner {...props} />);
      expect(enzymeWrapper.find('div').hasClass('spinner-opacity-0')).toBe(true);
    });

  });
});
