import { render, screen, fireEvent } from '@testing-library/react';
import Button from './Button';
import { vi, describe, it, expect } from 'vitest';

describe('Button Component', () => {
  it('renders the button with the correct label', () => {
    render(<Button label="Submit" />);
    expect(screen.getByText('Submit')).toBeInTheDocument();
  });

  it('calls the onClick function when clicked', () => {
    const handleClick = vi.fn(); 
    render(<Button label="Submit" onClick={handleClick} />);
    
    fireEvent.click(screen.getByText('Submit'));
    
    expect(handleClick).toHaveBeenCalledTimes(1);
  });
});