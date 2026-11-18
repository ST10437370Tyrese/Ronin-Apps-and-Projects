import Card from './Card';

export default {
  title: 'Components/Card',
  component: Card,
  tags: ['autodocs'],
};

export const Default = {
  render: (args) => <Card {...args} />,
  args: {
    title: 'Project Overview',
    children: 'This is a summary of the current project metrics.',
  },
};

export const NoTitle = {
  render: (args) => <Card {...args} />,
  args: {
    children: 'This card does not have a header.',
  },
};