import Input from './Input';

export default {
  title: 'Components/Input',
  component: Input,
  tags: ['autodocs'],
  argTypes: {
    type: { control: 'select', options: ['text', 'email', 'password', 'number'] },
    onChange: { action: 'changed' },
  },
};

export const TextInput = {
  render: (args) => <Input {...args} />,
  args: { label: 'Full Name', type: 'text', placeholder: 'Enter your name' },
};

export const EmailInput = {
  render: (args) => <Input {...args} />,
  args: { label: 'Email Address', type: 'email', placeholder: 'you@example.com' },
};