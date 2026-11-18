import Button from './Button';

export default {
  title: 'Components/Button',
  component: Button,
  tags: ['autodocs'],
  argTypes: {
    variant: {
      control: { type: 'select' },
      options: ['primary', 'secondary'],
    },
    disabled: { control: 'boolean' },
    label: { control: 'text' },
    onClick: { action: 'clicked' },
  },
};

export const Primary = {
  render:(args) => <Button {...args} />,
  args:{
    label:'Click Me',
    variant:'primary',
    disabled:false
  },
};

export const Secondary = {
  render: (args) => <Button {...args} />,
  args: { label: 'Cancel', variant: 'secondary' },
};

export const Disabled = {
  render: (args) => <Button {...args} />,
  args: { label: 'Cannot Click', disabled: true },
};