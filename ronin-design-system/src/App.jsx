import Button from './components/Button/Button';
import Card from './components/Card/Card';
import Input from './components/Input/Input';
import Modal from './components/Modal/Modal';
import { useState } from 'react';

export default function App() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '800px', margin: '0 auto' }}>
      <h1>Ronin Design System</h1>
      <p>A collection of reusable UI components.</p>

      <hr style={{ margin: '40px 0' }} />

      <h2>Buttons</h2>
      <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap' }}>
        <Button label="Primary" variant="primary" onClick={() => alert('Clicked!')} />
        <Button label="Secondary" variant="secondary" onClick={() => alert('Clicked!')} />
        <Button label="Disabled" variant="primary" disabled />
      </div>

      <hr style={{ margin: '40px 0' }} />

      <h2>Inputs</h2>
      <div style={{ maxWidth: '300px' }}>
        <Input label="Full Name" type="text" placeholder="John Doe" />
        <Input label="Email" type="email" placeholder="john@example.com" />
      </div>

      <hr style={{ margin: '40px 0' }} />

      <h2>Cards</h2>
      <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap' }}>
        <Card title="Project Alpha">
          <p>Status: In Progress</p>
          <p>Deadline: Nov 30</p>
        </Card>
        <Card title="Project Beta">
          <p>Status: Completed</p>
          <p>Deadline: Oct 15</p>
        </Card>
      </div>

      <hr style={{ margin: '40px 0' }} />

      <h2>Modal</h2>
      <Button label="Open Modal" variant="primary" onClick={() => setIsModalOpen(true)} />
      <Modal 
        isOpen={isModalOpen} 
        onClose={() => setIsModalOpen(false)} 
        title="Test Modal"
      >
        <p>This is the modal content. It is fully responsive.</p>
        <Button label="Close" variant="secondary" onClick={() => setIsModalOpen(false)} />
      </Modal>
    </div>
  );
}