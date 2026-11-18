import Modal from './Modal';

export default {
  title: 'Components/Modal',
  component: Modal,
  tags: ['autodocs'],
  argTypes: {
    onClose: { action: 'closed' },
  },
};

export const OpenModal = {
  render: (args) => <Modal {...args} />,
  args: {
    isOpen: true,
    title: 'Confirm Deletion',
    children: 'Are you sure you want to delete this project?',
  },
};